
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package in.gravitos.blobcatplush.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.BlockItem;

import in.gravitos.blobcatplush.item.BlobcatadvancementitemItem;
import in.gravitos.blobcatplush.BlobcatplushMod;

public class BlobcatplushModItems {
	public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, BlobcatplushMod.MODID);
	public static final RegistryObject<Item> BLOBCAT = block(BlobcatplushModBlocks.BLOBCAT);
	public static final RegistryObject<Item> BLOBCATADVANCEMENTITEM = REGISTRY.register("blobcatadvancementitem", () -> new BlobcatadvancementitemItem());
	public static final RegistryObject<Item> BLOBFOX = block(BlobcatplushModBlocks.BLOBFOX);

	// Start of user code block custom items
	// End of user code block custom items
	private static RegistryObject<Item> block(RegistryObject<Block> block) {
		return REGISTRY.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties()));
	}
}
