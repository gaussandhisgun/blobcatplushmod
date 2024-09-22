
package in.gravitos.blobcatplush.block;

import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.entity.Mob;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

public class BlobcatBlock extends Block implements SimpleWaterloggedBlock {
	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

	public BlobcatBlock() {
		super(BlockBehaviour.Properties.of().ignitedByLava().mapColor(MapColor.COLOR_YELLOW).sound(SoundType.WOOL).strength(1f, 10f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, false));
	}

	@Override
	public float[] getBeaconColorMultiplier(BlockState state, LevelReader world, BlockPos pos, BlockPos beaconPos) {
		return new float[]{1f, 1f, 0f};
	}

	@Override
	public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
		return state.getFluidState().isEmpty();
	}

	@Override
	public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
		return 0;
	}

	@Override
	public VoxelShape getVisualShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return Shapes.empty();
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return switch (state.getValue(FACING)) {
			default -> Shapes.or(box(4, 1, 3, 12, 9, 13), box(4, 9, 4, 12, 10, 12), box(4, 0, 4, 12, 1, 12), box(12, 1, 4, 13, 9, 12), box(3, 1, 4, 4, 9, 12), box(12, 9, 7, 13, 11, 9), box(3, 9, 7, 4, 11, 9), box(10, 10, 7, 12, 12, 9),
					box(4, 10, 7, 6, 12, 9), box(9, 10, 7, 10, 11, 9), box(6, 10, 7, 7, 11, 9));
			case NORTH -> Shapes.or(box(4, 1, 3, 12, 9, 13), box(4, 9, 4, 12, 10, 12), box(4, 0, 4, 12, 1, 12), box(3, 1, 4, 4, 9, 12), box(12, 1, 4, 13, 9, 12), box(3, 9, 7, 4, 11, 9), box(12, 9, 7, 13, 11, 9), box(4, 10, 7, 6, 12, 9),
					box(10, 10, 7, 12, 12, 9), box(6, 10, 7, 7, 11, 9), box(9, 10, 7, 10, 11, 9));
			case EAST -> Shapes.or(box(3, 1, 4, 13, 9, 12), box(4, 9, 4, 12, 10, 12), box(4, 0, 4, 12, 1, 12), box(4, 1, 3, 12, 9, 4), box(4, 1, 12, 12, 9, 13), box(7, 9, 3, 9, 11, 4), box(7, 9, 12, 9, 11, 13), box(7, 10, 4, 9, 12, 6),
					box(7, 10, 10, 9, 12, 12), box(7, 10, 6, 9, 11, 7), box(7, 10, 9, 9, 11, 10));
			case WEST -> Shapes.or(box(3, 1, 4, 13, 9, 12), box(4, 9, 4, 12, 10, 12), box(4, 0, 4, 12, 1, 12), box(4, 1, 12, 12, 9, 13), box(4, 1, 3, 12, 9, 4), box(7, 9, 12, 9, 11, 13), box(7, 9, 3, 9, 11, 4), box(7, 10, 10, 9, 12, 12),
					box(7, 10, 4, 9, 12, 6), box(7, 10, 9, 9, 11, 10), box(7, 10, 6, 9, 11, 7));
		};
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING, WATERLOGGED);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		boolean flag = context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER;
		return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(WATERLOGGED, flag);
	}

	public BlockState rotate(BlockState state, Rotation rot) {
		return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
	}

	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@Override
	public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor world, BlockPos currentPos, BlockPos facingPos) {
		if (state.getValue(WATERLOGGED)) {
			world.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
		}
		return super.updateShape(state, facing, facingState, world, currentPos, facingPos);
	}

	@Override
	public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
		return 20;
	}

	@Override
	public BlockPathTypes getBlockPathType(BlockState state, BlockGetter world, BlockPos pos, Mob entity) {
		return BlockPathTypes.BLOCKED;
	}
}
