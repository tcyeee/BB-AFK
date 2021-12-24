//package top.tcyeee.world;
//
//import org.bukkit.Material;
//import org.bukkit.World;
//import org.bukkit.block.Biome;
//import org.bukkit.generator.ChunkGenerator;
//
//import java.util.Random;
//
///**
// * @author tcyeee
// * @date 2021/12/23 18:39
// */
//public class Plains extends ChunkGenerator {
//
//    @Override
//    public ChunkData generateChunkData(World w, Random r, int x, int z, BiomeGrid b) {
//        ChunkData chunkData = createChunkData(w); //创建区块数据
//
//        //下面这行方法调用参数中, 前三个参数代表一个XYZ对, 后面又是一个XYZ对.
//        //这两个XYZ对是选区的意思, 你可以结合Residence插件圈地、WorldEdit选区的思路思考.
//        //提醒: 一个Chunk的X、Z取值是0-16, Y取值是0-255.
//        chunkData.setRegion(0, 0, 0, 16, 2, 16, Material.BEDROCK);  //填充基岩
//        chunkData.setRegion(0, 2, 0, 16, 3, 16, Material.GRASS_BLOCK); //填充草方块
//
//        //将整个区块都设置为平原生物群系(PLAINS)
//        for (int i = 0; i < 16; i++) {
//            for (int j = 0; j < 16; j++) {
//                b.setBiome(i, j, Biome.PLAINS);
//            }
//        }
//        return chunkData;
//    }
//}
