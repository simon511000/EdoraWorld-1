package eu.octanne.edora.mixin;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.google.gson.JsonElement;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import eu.octanne.edora.enums.EdoraSlabType;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.enums.SlabType;
import net.minecraft.data.client.model.BlockStateModelGenerator;
import net.minecraft.data.client.model.BlockStateSupplier;
import net.minecraft.data.client.model.BlockStateVariant;
import net.minecraft.data.client.model.BlockStateVariantMap;
import net.minecraft.data.client.model.Models;
import net.minecraft.data.client.model.Texture;
import net.minecraft.data.client.model.TextureKey;
import net.minecraft.data.client.model.VariantSettings;
import net.minecraft.data.client.model.VariantsBlockStateSupplier;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.Identifier;

@Mixin(BlockStateModelGenerator.class)
public class MixinProperties {

    @Shadow
    private final Consumer<BlockStateSupplier> blockStateCollector;
    @Shadow
    private final BiConsumer<Identifier, Supplier<JsonElement>> modelCollector = null;
    @Shadow
    private final Consumer<BlockStateSupplier> blockStateCollector = null;
    @Shadow
    private Identifier baseModelId;

    @Shadow
    private static VariantsBlockStateSupplier createSingletonBlockState(Block block, Identifier modelId) {
        return null;
    }

    @Overwrite
    private static BlockStateSupplier createSlabBlockState(Block slabBlock, Identifier bottomModelId, Identifier topModelId, Identifier fullModelId) {
        return VariantsBlockStateSupplier.create(slabBlock).coordinate(BlockStateVariantMap.create(EdoraSlabType.SLAB_TYPE).register(EdoraSlabType.BOTTOM, (BlockStateVariant)BlockStateVariant.create().put(VariantSettings.MODEL, bottomModelId)).register(EdoraSlabType.TOP, (BlockStateVariant)BlockStateVariant.create().put(VariantSettings.MODEL, topModelId)).register(EdoraSlabType.DOUBLE, (BlockStateVariant)BlockStateVariant.create().put(VariantSettings.MODEL, fullModelId)));
    }

    @Overwrite
    private void registerSmoothStone() {
        Texture texture = Texture.all(Blocks.SMOOTH_STONE);
        Texture texture2 = Texture.sideEnd(Texture.getSubId(Blocks.SMOOTH_STONE_SLAB, "_side"), texture.getTexture(TextureKey.TOP));
        Identifier identifier = Models.SLAB.upload(Blocks.SMOOTH_STONE_SLAB, texture2, this.modelCollector);
        Identifier identifier2 = Models.SLAB_TOP.upload(Blocks.SMOOTH_STONE_SLAB, texture2, this.modelCollector);
        Identifier identifier3 = Models.CUBE_COLUMN.uploadWithoutVariant(Blocks.SMOOTH_STONE_SLAB, "_double", texture2, this.modelCollector);
        this.blockStateCollector.accept(createSlabBlockState(Blocks.SMOOTH_STONE_SLAB, identifier, identifier2, identifier3));
        this.blockStateCollector.accept(createSingletonBlockState(Blocks.SMOOTH_STONE, Models.CUBE_ALL.upload(Blocks.SMOOTH_STONE, texture, this.modelCollector)));
    }

    @Overwrite  
    public Object /* BlockStateModelGenerator.BlockTexturePool */ slab(Block slabBlock) {
        if (this.baseModelId == null) {
                throw new IllegalStateException("Full block not generated yet");
        } else {
            Identifier identifier = Models.SLAB.upload(slabBlock, this.texture, BlockStateModelGenerator.this.modelCollector);
            Identifier identifier2 = Models.SLAB_TOP.upload(slabBlock, this.texture, BlockStateModelGenerator.this.modelCollector);
            this.blockStateCollector.accept(createSlabBlockState(slabBlock, identifier, identifier2, this.baseModelId));
            return this;
        }
    }

}