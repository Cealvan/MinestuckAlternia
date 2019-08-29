package main.java.com.apocfarce.minestuck_alternia.world;

import java.util.function.Function;

import javax.annotation.Nullable;

import main.java.com.apocfarce.minestuck_alternia.world.biome.provider.AlterniaBiomeProvider;
import main.java.com.apocfarce.minestuck_alternia.world.biome.provider.AlterniaBiomeProviderType;
import main.java.com.apocfarce.minestuck_alternia.world.biome.provider.AlterniaBiomeSettings;
import main.java.com.apocfarce.minestuck_alternia.world.gen.AlterniaChunkGenerator;
import main.java.com.apocfarce.minestuck_alternia.world.gen.AlterniaGenSettings;
import main.java.com.apocfarce.minestuck_alternia.world.gen.AlterniaGenTypeHandeler;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.ChunkGeneratorType;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ModDimension;

public class AlterniaDimension extends Dimension {
   private final DimensionType type;
   public AlterniaDimension() { this(AlterniaDimensionsHandeler.alterniaType); }
   public AlterniaDimension(DimensionType type) {
      this.type = type;
   }

   public DimensionType getType() {
      return type;
   }


   public boolean canDropChunk(int x, int z) {
      return (this.type != AlterniaDimensionsHandeler.alterniaType || !this.world.isSpawnChunk(x, z)) && super.canDropChunk(x, z);
   }


   protected void init() {
      this.hasSkyLight = true;
   }

   public IChunkGenerator<? extends IChunkGenSettings> createChunkGenerator() {

//	   --------------------------------MINESTUCK ALGORYTHIM------------------------
//		AlterniaGenSettings settings = AlterniaGenTypeHandeler.ALTERNIA.createSettings();
//		settings.setDefautBlock(MinestuckBlocks.WHITE_CHESS_DIRT.getDefaultState());
//		settings.setDefaultFluid(Blocks.AIR.getDefaultState());
//		return AlterniaGenTypeHandeler.ALTERNIA.create(this.world, BiomeProviderType.FIXED.create(BiomeProviderType.FIXED.createSettings().setBiome(AlterniaBiomeHandeler.alterniaPlaines)), settings);

		
//		-------------------------------OVERWORLD ALGORyTHIM-------------------------		
		ChunkGeneratorType<AlterniaGenSettings,AlterniaChunkGenerator> alterniaChunkGenerator = AlterniaGenTypeHandeler.ALTERNIA;
		AlterniaBiomeProviderType<AlterniaBiomeSettings, AlterniaBiomeProvider> biomeprovidertype1 = AlterniaBiomeProviderType.ALTERNIA;
		
	      
		AlterniaGenSettings alterniagensettings = alterniaChunkGenerator.createSettings();
		AlterniaBiomeSettings overworldbiomeprovidersettings = biomeprovidertype1.createSettings().setWorldInfo(this.world.getWorldInfo()).setGeneratorSettings(alterniagensettings);
		return alterniaChunkGenerator.create(this.world, biomeprovidertype1.create(overworldbiomeprovidersettings), alterniagensettings);
	   
//		------------------------------MIDNIGHT ALGORYTHEM-----------------------------	   
//	   long seed = world.getSeed();
//	   AlterniaGenSettings settings = AlterniaGenTypeHandeler.ALTERNIA.createSettings();
//	   BiomeLayers<Biome> surfaceLayers = BiomeLayerHandeler.SURFACE.make(seed);
//	   BiomeLayers<CavernousBiome> undergroundLayers = BiomeLayerHandeler.UNDERGROUND.make(seed);

//	   return new AlterniaChunkGenerator(this.world, surfaceLayers, undergroundLayers, settings);

	 
  }

   @Nullable
   public BlockPos findSpawn(ChunkPos p_206920_1_, boolean checkValid) {
      for(int i = p_206920_1_.getXStart(); i <= p_206920_1_.getXEnd(); ++i) {
         for(int j = p_206920_1_.getZStart(); j <= p_206920_1_.getZEnd(); ++j) {
            BlockPos blockpos = this.findSpawn(i, j, checkValid);
            if (blockpos != null) {
               return blockpos;
            }
         }
      }

      return null;
   }

   @Nullable
   public BlockPos findSpawn(int p_206921_1_, int p_206921_2_, boolean checkValid) {
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(p_206921_1_, 0, p_206921_2_);
      Biome biome = this.world.getBiome(blockpos$mutableblockpos);
      IBlockState iblockstate = biome.getSurfaceBuilderConfig().getTop();
      if (checkValid && !iblockstate.getBlock().isIn(BlockTags.VALID_SPAWN)) {
         return null;
      } else {
         Chunk chunk = this.world.getChunk(p_206921_1_ >> 4, p_206921_2_ >> 4);
         int i = chunk.getTopBlockY(Heightmap.Type.MOTION_BLOCKING, p_206921_1_ & 15, p_206921_2_ & 15);
         if (i < 0) {
            return null;
         } else if (chunk.getTopBlockY(Heightmap.Type.WORLD_SURFACE, p_206921_1_ & 15, p_206921_2_ & 15) > chunk.getTopBlockY(Heightmap.Type.OCEAN_FLOOR, p_206921_1_ & 15, p_206921_2_ & 15)) {
            return null;
         } else {
            for(int j = i + 1; j >= 0; --j) {
               blockpos$mutableblockpos.setPos(p_206921_1_, j, p_206921_2_);
               IBlockState iblockstate1 = this.world.getBlockState(blockpos$mutableblockpos);
               if (!iblockstate1.getFluidState().isEmpty()) {
                  break;
               }

               if (iblockstate1.equals(iblockstate)) {
                  return blockpos$mutableblockpos.up().toImmutable();
               }
            }

            return null;
         }
      }
   }

   /**
    * Calculates the angle of sun and moon in the sky relative to a specified time (usually worldTime)
    */
   public float calculateCelestialAngle(long worldTime, float partialTicks) {
      int i = (int)(worldTime % 24000L);
      float f = ((float)i + partialTicks) / 24000.0F - 0.25F;
      if (f < 0.0F) {
         ++f;
      }

      if (f > 1.0F) {
         --f;
      }

      float f1 = 1.0F - (float)((Math.cos((double)f * Math.PI) + 1.0D) / 2.0D);
      f = f + (f1 - f) / 3.0F;
      return f;
   }

   /**
    * Returns 'true' if in the "main surface world", but 'false' if in the Nether or End dimensions.
    */
   public boolean isSurfaceWorld() {
      return true;
   }

   /**
    * Return Vec3D with biome specific fog color
    */
   @OnlyIn(Dist.CLIENT)
   public Vec3d getFogColor(float p_76562_1_, float p_76562_2_) {
      float f = MathHelper.cos(p_76562_1_ * ((float)Math.PI * 2F)) * 2.0F + 0.5F;
      f = MathHelper.clamp(f, 0.0F, 1.0F);
      float f1 = 0.7529412F;
      float f2 = 0.84705883F;
      float f3 = 1.0F;
      f1 = f1 * (f * 0.94F + 0.06F);
      f2 = f2 * (f * 0.94F + 0.06F);
      f3 = f3 * (f * 0.91F + 0.09F);
      return new Vec3d((double)f1, (double)f2, (double)f3);
   }

   /**
    * True if the player can respawn in this dimension (true = overworld, false = nether).
    */
   public boolean canRespawnHere() {
      return true;
   }

   /**
    * Returns true if the given X,Z coordinate should show environmental fog.
    */
   @OnlyIn(Dist.CLIENT)
   public boolean doesXZShowFog(int x, int z) {
      return false;
   }
   
   
	
	public static class Type extends ModDimension
	{
		@Override
		public Function<DimensionType, ? extends Dimension> getFactory()
		{
			return AlterniaDimension::new;
		}
	}
}