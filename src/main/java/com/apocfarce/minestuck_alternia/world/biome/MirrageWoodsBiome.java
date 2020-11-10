package com.apocfarce.minestuck_alternia.world.biome;

import java.util.ArrayList;
import java.util.List;

import com.apocfarce.minestuck_alternia.block.AlterniaBlocks;
import com.apocfarce.minestuck_alternia.world.gen.feature.AlterniaFeatureHandeler;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.ConfiguredRandomFeatureList;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.MultipleRandomFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.FrequencyConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;

public class MirrageWoodsBiome extends Biome {

	protected MirrageWoodsBiome() {
		super(new Biome.Builder()
				.surfaceBuilder(SurfaceBuilder.DEFAULT,SurfaceBuilder.GRASS_DIRT_SAND_CONFIG)
				.precipitation(Biome.RainType.RAIN)
				.category(Biome.Category.FOREST)
				.temperature(0.5F)
				.depth(0.2F)
				.scale(0.2F)
				.temperature(0.25F)
				.downfall(0.8F)
				.waterColor(0x113355)
				.waterFogColor(0x115588)
				.parent((String)null)
				);
		
		
		List<ConfiguredRandomFeatureList<?>> biomeHives = new ArrayList<ConfiguredRandomFeatureList<?>>();
		

		
		biomeHives.add(AlterniaFeatureHandeler.mutantHiveFeature1.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withChance(0.1F));
		biomeHives.add(AlterniaFeatureHandeler.mutantHiveFeature2.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withChance(0.3F));
		biomeHives.add(AlterniaFeatureHandeler.mutantHiveFeature3.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withChance(0.1F));
		
		biomeHives.add(AlterniaFeatureHandeler.limeHiveFeature1.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withChance(0.1F));
		biomeHives.add(AlterniaFeatureHandeler.limeHiveFeature2.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withChance(0.1F));
		biomeHives.add(AlterniaFeatureHandeler.limeHiveFeature3.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withChance(0.1F));

		biomeHives.add(AlterniaFeatureHandeler.oliveHiveFeature1.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withChance(0.1F));
		biomeHives.add(AlterniaFeatureHandeler.oliveHiveFeature2.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withChance(1F));
		biomeHives.add(AlterniaFeatureHandeler.oliveHiveFeature3.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withChance(0.3F));

		ConfiguredFeature<?, ?> defaultHive = AlterniaFeatureHandeler.mutantHiveFeature1.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);
		this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(biomeHives, defaultHive)).withPlacement(Placement.CHANCE_HEIGHTMAP.configure(new ChanceConfig(50))));

		
		DefaultBiomeFeatures.addCarvers(this);
		DefaultBiomeFeatures.addStructures(this);
		DefaultBiomeFeatures.addLakes(this);
		DefaultBiomeFeatures.addMonsterRooms(this);
		DefaultBiomeFeatures.addStoneVariants(this);
		DefaultBiomeFeatures.addOres(this);
		DefaultBiomeFeatures.addSedimentDisks(this);
		
		AlterniaBiomeFeatures.addMirrageTrees(this);
//	      addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(createGrassClusterConfig(AlterniaBlocks.mirrageGrass.getDefaultState())).withPlacement(Placement.COUNT_HEIGHTMAP.configure(new FrequencyConfig(1))));
		

	      DefaultBiomeFeatures.addExtraDefaultFlowers(this);
	      DefaultBiomeFeatures.addJungleGrass(this);
	      DefaultBiomeFeatures.addMushrooms(this);
	      DefaultBiomeFeatures.addSprings(this);
	      DefaultBiomeFeatures.addFreezeTopLayer(this);
		

		
	}
	@Override
	public int getFoliageColor() {
		return(0x666666);
	}
	@Override
	public int getGrassColor(double posX, double posZ) {
		return(0x666666);
	}


}
