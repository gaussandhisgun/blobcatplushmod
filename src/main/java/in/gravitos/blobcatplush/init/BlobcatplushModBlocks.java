
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package in.gravitos.blobcatplush.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.level.block.Block;

import in.gravitos.blobcatplush.block.BlobcatBlock;
import in.gravitos.blobcatplush.BlobcatplushMod;

public class BlobcatplushModBlocks {
	public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, BlobcatplushMod.MODID);
	public static final RegistryObject<Block> BLOBCAT = REGISTRY.register("blobcat", () -> new BlobcatBlock());
	// Start of user code block custom blocks
	// End of user code block custom blocks
}
