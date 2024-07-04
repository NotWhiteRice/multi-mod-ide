package io.github.deprecated.v1.circuitsmod.block;

import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

public class BlockInit {
    //Redstone transmission
    public static RegistryObject<Block> blockSignalEmitter;

    public static RegistryObject<Block> blockRedstonePanel;

    //Basic circuits
    public static RegistryObject<Block> blockNotGate;
    public static RegistryObject<Block> blockAndGate;
    public static RegistryObject<Block> blockOrGate;
    public static RegistryObject<Block> blockXorGate;
    public static RegistryObject<Block> blockNandGate;
    public static RegistryObject<Block> blockNorGate;
    public static RegistryObject<Block> blockXnorGate;
    public static RegistryObject<Block> blockRedstoneBridge;


    public static void initBlocks() {
        //blockRedstonePanel = ModRegistry.registerBlock(Circuits.modID, "redstone_panel", RedstonePanelBlock::new);

        //blockNotGate = ModRegistry.registerBlock(Circuits.modID, "not_gate", NotGateBlock::new);
        //blockAndGate = ModRegistry.registerBlock(Circuits.modID, "and_gate", AndGateBlock::new);
        //blockOrGate = ModRegistry.registerBlock(Circuits.modID, "or_gate", OrGateBlock::new);
        //blockXorGate = ModRegistry.registerBlock(Circuits.modID, "xor_gate", XorGateBlock::new);
        //blockNandGate = ModRegistry.registerBlock(Circuits.modID, "nand_gate", NandGateBlock::new);
        //blockNorGate = ModRegistry.registerBlock(Circuits.modID, "nor_gate", NorGateBlock::new);
        //blockXnorGate = ModRegistry.registerBlock(Circuits.modID, "xnor_gate", XnorGateBlock::new);
        //blockRedstoneBridge = ModRegistry.registerBlock(Circuits.modID, "redstone_bridge", RedstoneBridgeBlock::new);
    }
}
