package eu.octanne.edora.mixin.block;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import eu.octanne.edora.enums.EdoraSlabType;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.Waterloggable;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.block.BlockState;

@Mixin(SlabBlock.class)
public class MixinSlabBlock extends Block implements Waterloggable {

    public static final EnumProperty<EdoraSlabType> TYPE;
    @Shadow
    public static final BooleanProperty WATERLOGGED;

    protected static final VoxelShape WEST_SHAPE;
    protected static final VoxelShape EAST_SHAPE;
    protected static final VoxelShape NORTH_SHAPE;
    protected static final VoxelShape SOUTH_SHAPE;

    public MixinSlabBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)this.getDefaultState().with(TYPE, EdoraSlabType.NORTH)).with(WATERLOGGED, false));
    }
    
    static {
        TYPE = EdoraSlabType.SLAB_TYPE;
        WATERLOGGED = Properties.WATERLOGGED;
        NORTH_SHAPE = Block.createCuboidShape(0D, 0D, 0D, 16D, 16D, 8D);
        SOUTH_SHAPE = Block.createCuboidShape(0D, 0D, 8D, 16D, 16D, 16D);
        WEST_SHAPE = Block.createCuboidShape(0D, 0D, 0D, 8D, 16D, 16D);
        EAST_SHAPE = Block.createCuboidShape(8D, 0D, 0D, 16D, 16D, 16D);
    }

}