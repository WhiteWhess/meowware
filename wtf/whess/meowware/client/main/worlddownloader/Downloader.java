package wtf.whess.meowware.client.main.worlddownloader;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import wtf.whess.meowware.client.api.utilities.Utility;
import wtf.whess.meowware.client.api.utilities.client.ChatUtil;

import java.util.ArrayList;
import java.util.Objects;

public class Downloader extends Utility {
    public static ArrayList<WorldBlock> blocks = new ArrayList<>();
    public static ArrayList<BlockPos> loadedBlockPos = new ArrayList<>();

    private static double xPos, yPos, zPos;

    public static void pasteBlocks(ArrayList<WorldBlock> loadedBlockPosList) {
        new Thread(() -> {
            try {
                for (WorldBlock block : loadedBlockPosList) {
                    if (block != null && mc.getIntegratedServer() != null) {
                        Thread.sleep(2);
                        mc.getIntegratedServer().worldServers[0].setBlockState(block.position, block.state);
                    }
                }
            } catch (Exception ignored) {}
        }).start();
    }

    public static void downloadBlocks(int size) {
        xPos = mc.player.posX;
        yPos = mc.player.posY;
        zPos = mc.player.posZ;
        ChatUtil.printChat("Начата скачка блоков, ожидайте сообщения.");
        int sizeOther = Math.round(size / 2F);
        new Thread(() -> {
            try {
                for (int x = -size; x < size + sizeOther; x++) {
                    for (int z = -size; z < size + sizeOther; z++) {
                        for (int y = -size; y < size + sizeOther; y++) {
                            int blockX = (int) (xPos + x);
                            int blockY = (int) (yPos + y);
                            int blockZ = (int) (zPos + z);
                            BlockPos blockPos = new BlockPos(blockX, blockY, blockZ);
                            IBlockState block = mc.world.getBlockState(blockPos);
                            if (!mc.world.isAirBlock(blockPos) && block != null && !loadedBlockPos.contains(blockPos)) {
                                loadedBlockPos.add(blockPos);
                                blocks.add(new WorldBlock(block, blockPos));
                                ChatUtil.printChat("Block saved #" + blocks.size());
                            }
                        }
                    }
                }
            } catch (Exception ignored) {}
            blocks.removeIf(Objects::isNull);
            ChatUtil.printChat("Выкачка завершена!");
        }).start();
    }

    public static void reset() {
        blocks.clear();
        loadedBlockPos.clear();
    }

    private static class WorldBlock {
        public IBlockState state;
        public BlockPos position;

        public WorldBlock(IBlockState state, BlockPos position) {
            this.state = state;
            this.position = position;
        }

        @Override
        public String toString() {
            return state.getBlock().isCollidable() + " " + position.toString();
        }
    }

}
