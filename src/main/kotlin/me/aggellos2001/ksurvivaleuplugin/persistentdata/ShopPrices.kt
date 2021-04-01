package me.aggellos2001.ksurvivaleuplugin.persistentdata

import org.bukkit.Material

object ShopPrices {

    fun Material.getShopPrice(): Double {
        return when (this) {
            Material.STONE -> 2.1
            Material.SMOOTH_STONE -> 3.26
            Material.POLISHED_GRANITE -> 1.05
            Material.COARSE_DIRT -> 0.53
            Material.OAK_PLANKS, Material.ACACIA_PLANKS, Material.BIRCH_PLANKS, Material.DARK_OAK_PLANKS, Material.JUNGLE_PLANKS, Material.SPRUCE_PLANKS -> 2.63
            Material.OAK_WOOD, Material.SPRUCE_WOOD, Material.BIRCH_WOOD, Material.JUNGLE_WOOD -> 14.0
            Material.SPONGE -> 11.55
            Material.GLASS -> 1.58
            Material.LAPIS_BLOCK -> 28.35
            Material.DISPENSER -> 20.02
            Material.SANDSTONE -> 2.1
            Material.CHISELED_SANDSTONE -> 2.32
            Material.CUT_SANDSTONE -> 2.21
            Material.SMOOTH_SANDSTONE -> 3.26
            Material.NOTE_BLOCK -> 25.20
            Material.POWERED_RAIL -> 56.99
            Material.DETECTOR_RAIL -> 18.94
            Material.STICKY_PISTON -> 45.42
            Material.PISTON -> 33.26
            Material.ORANGE_WOOL -> 5.83
            Material.MAGENTA_WOOL -> 6.62
            Material.LIGHT_BLUE_WOOL -> 7.28
            Material.YELLOW_WOOL -> 5.8
            Material.LIME_WOOL -> 9.74
            Material.PINK_WOOL -> 6.55
            Material.GRAY_WOOL -> 8.0
            Material.LIGHT_GRAY_WOOL -> 7.64
            Material.CYAN_WOOL -> 9.74
            Material.PURPLE_WOOL -> 10.76
            Material.BLUE_WOOL -> 7.18
            Material.BROWN_WOOL -> 10.76
            Material.GREEN_WOOL -> 11.87
            Material.RED_WOOL -> 5.8
            Material.BLACK_WOOL -> 8.56
            Material.GOLD_BLOCK -> 0.0
            Material.IRON_BLOCK -> 158.76
            Material.SMOOTH_STONE_SLAB -> 1.71
            Material.SANDSTONE_SLAB -> 1.1
            Material.COBBLESTONE_SLAB -> 0.53
            Material.BRICK_SLAB -> 9.26
            Material.STONE_BRICK_SLAB -> 1.16
            Material.NETHER_BRICK_SLAB -> 13.89
            Material.QUARTZ_SLAB -> 38.59
            Material.STONE_SLAB -> 1.1
            Material.ANDESITE_SLAB -> 0.53
            Material.POLISHED_ANDESITE_SLAB -> 0.55
            Material.DIORITE_SLAB -> 0.53
            Material.POLISHED_DIORITE_SLAB -> 0.55
            Material.GRANITE_SLAB -> 0.53
            Material.POLISHED_GRANITE_SLAB -> 0.55
            Material.MOSSY_STONE_BRICK_SLAB -> 3.97
            Material.MOSSY_COBBLESTONE_SLAB -> 3.31
            Material.SMOOTH_SANDSTONE_SLAB -> 1.71
            Material.SMOOTH_RED_SANDSTONE_SLAB -> 4.02
            Material.SMOOTH_QUARTZ_SLAB -> 41.07
            Material.RED_NETHER_BRICK_SLAB -> 18.19
            Material.END_STONE_BRICK_SLAB -> 16.18
            Material.BRICKS -> 17.64
            Material.TNT -> 54.60
            Material.BOOKSHELF -> 46.55
            Material.MOSSY_COBBLESTONE -> 6.3
            Material.TORCH -> 2.46
            Material.OAK_STAIRS -> 4.13
            Material.CHEST -> 22.05
            Material.DIAMOND_BLOCK -> 0.0
            Material.CRAFTING_TABLE -> 11.03
            Material.FURNACE -> 8.4
            Material.LADDER -> 3.38
            Material.RAIL -> 6.71
            Material.COBBLESTONE_STAIRS -> 1.58
            Material.LEVER -> 2.5
            Material.STONE_PRESSURE_PLATE -> 4.41
            Material.OAK_PRESSURE_PLATE, Material.SPRUCE_PRESSURE_PLATE, Material.BIRCH_PRESSURE_PLATE, Material.JUNGLE_PRESSURE_PLATE, Material.ACACIA_PRESSURE_PLATE, Material.DARK_OAK_PRESSURE_PLATE -> 5.51
            Material.REDSTONE_TORCH -> 4.6
            Material.STONE_BUTTON -> 4.41
            Material.SNOW_BLOCK -> 4.2
            Material.CLAY -> 12.6
            Material.JUKEBOX -> 547.05
            Material.OAK_FENCE -> 4.64
            Material.GLOWSTONE -> 33.6
            Material.JACK_O_LANTERN -> 7.83
            Material.WHITE_STAINED_GLASS -> 1.88
            Material.ORANGE_STAINED_GLASS -> 1.73
            Material.MAGENTA_STAINED_GLASS -> 1.82
            Material.LIGHT_BLUE_STAINED_GLASS -> 1.91
            Material.YELLOW_STAINED_GLASS -> 1.72
            Material.LIME_STAINED_GLASS -> 2.21
            Material.PINK_STAINED_GLASS -> 1.82
            Material.GRAY_STAINED_GLASS -> 2.0
            Material.LIGHT_GRAY_STAINED_GLASS -> 1.95
            Material.PURPLE_STAINED_GLASS -> 1.82
            Material.BLUE_STAINED_GLASS -> 1.89
            Material.BROWN_STAINED_GLASS -> 2.34
            Material.GREEN_STAINED_GLASS -> 2.48
            Material.RED_STAINED_GLASS -> 1.72
            Material.BLACK_STAINED_GLASS -> 2.07
            Material.OAK_TRAPDOOR, Material.SPRUCE_TRAPDOOR, Material.BIRCH_TRAPDOOR, Material.JUNGLE_TRAPDOOR, Material.ACACIA_TRAPDOOR, Material.DARK_OAK_TRAPDOOR -> 8.27
            Material.STONE_BRICKS -> 2.21
            Material.MOSSY_STONE_BRICKS -> 7.57
            Material.CRACKED_STONE_BRICKS -> 3.37
            Material.CHISELED_STONE_BRICKS -> 2.43
            Material.IRON_BARS -> 6.62
            Material.GLASS_PANE -> 0.62
            Material.MELON -> 14.18
            Material.OAK_FENCE_GATE -> 11.30
            Material.BRICK_STAIRS -> 27.28
            Material.STONE_BRICK_STAIRS -> 3.47
            Material.STONE_STAIRS -> 3.31
            Material.ANDESITE_STAIRS -> 1.58
            Material.POLISHED_ANDESITE_STAIRS -> 1.65
            Material.DIORITE_STAIRS -> 1.58
            Material.POLISHED_DIORITE_STAIRS -> 1.65
            Material.GRANITE_STAIRS -> 1.58
            Material.POLISHED_GRANITE_STAIRS -> 1.65
            Material.MOSSY_STONE_BRICK_STAIRS -> 11.92
            Material.MOSSY_COBBLESTONE_STAIRS -> 9.92
            Material.SMOOTH_SANDSTONE_STAIRS -> 5.13
            Material.SMOOTH_RED_SANDSTONE_STAIRS -> 12.07
            Material.SMOOTH_QUARTZ_STAIRS -> 123.20
            Material.RED_NETHER_BRICK_STAIRS -> 54.57
            Material.END_STONE_BRICK_STAIRS -> 48.24
            Material.NETHER_BRICKS -> 26.46
            Material.CRACKED_NETHER_BRICKS -> 28.83
            Material.CHISELED_NETHER_BRICKS -> 29.17
            Material.NETHER_BRICK_FENCE -> 20.73
            Material.NETHER_BRICK_STAIRS -> 41.67
            Material.ENCHANTING_TABLE -> 1270.01
            Material.REDSTONE_LAMP -> 47.88
            Material.OAK_SLAB, Material.SPRUCE_SLAB, Material.BIRCH_SLAB, Material.JUNGLE_SLAB, Material.ACACIA_SLAB, Material.DARK_OAK_SLAB -> 1.38
            Material.SANDSTONE_STAIRS -> 0.79
            Material.ENDER_CHEST -> 390.08
            Material.TRIPWIRE_HOOK -> 10.92
            Material.EMERALD_BLOCK -> 0.0
            Material.SPRUCE_STAIRS, Material.BIRCH_STAIRS, Material.JUNGLE_STAIRS -> 4.13
            Material.BEACON -> 0.0
            Material.COBBLESTONE_WALL -> 1.05
            Material.MOSSY_COBBLESTONE_WALL -> 6.62
            Material.ANDESITE_WALL, Material.DIORITE_WALL, Material.GRANITE_WALL -> 1.05
            Material.SANDSTONE_WALL -> 2.21
            Material.RED_SANDSTONE_WALL -> 6.62
            Material.BRICK_WALL -> 18.52
            Material.STONE_BRICK_WALL -> 2.32
            Material.MOSSY_STONE_BRICK_WALL -> 7.94
            Material.NETHER_BRICK_WALL -> 27.28
            Material.RED_NETHER_BRICK_WALL -> 36.38
            Material.END_STONE_BRICK_WALL -> 32.16
            Material.PRISMARINE_WALL -> 22.05
            Material.OAK_BUTTON, Material.SPRUCE_BUTTON, Material.BIRCH_BUTTON, Material.JUNGLE_BUTTON, Material.ACACIA_BUTTON, Material.DARK_OAK_BUTTON -> 2.76
            Material.ANVIL -> 570.65
            Material.CHIPPED_ANVIL -> 285.33
            Material.DAMAGED_ANVIL -> 142.66
            Material.TRAPPED_CHEST -> 34.62
            Material.LIGHT_WEIGHTED_PRESSURE_PLATE -> 112.46
            Material.HEAVY_WEIGHTED_PRESSURE_PLATE -> 35.28
            Material.DAYLIGHT_DETECTOR -> 64.43
            Material.REDSTONE_BLOCK -> 28.35
            Material.HOPPER -> 111.35
            Material.QUARTZ_BLOCK -> 73.5
            Material.CHISELED_QUARTZ_BLOCK -> 81.03
            Material.QUARTZ_PILLAR -> 77.18
            Material.SMOOTH_QUARTZ -> 78.23
            Material.QUARTZ_BRICKS -> 77.18
            Material.QUARTZ_STAIRS -> 115.76
            Material.ACTIVATOR_RAIL -> 18.93
            Material.DROPPER -> 10.50
            Material.WHITE_TERRACOTTA -> 15.20
            Material.ORANGE_TERRACOTTA -> 15.17
            Material.MAGENTA_TERRACOTTA -> 15.19
            Material.LIGHT_BLUE_TERRACOTTA -> 19.62
            Material.YELLOW_TERRACOTTA -> 15.06
            Material.LIME_TERRACOTTA -> 15.18
            Material.PINK_TERRACOTTA -> 17.54
            Material.GRAY_TERRACOTTA -> 18.15
            Material.LIGHT_GRAY_TERRACOTTA -> 16.65
            Material.CYAN_TERRACOTTA -> 15.06
            Material.PURPLE_TERRACOTTA -> 15.65
            Material.BLUE_TERRACOTTA -> 15.24
            Material.BROWN_TERRACOTTA -> 15.68
            Material.GREEN_TERRACOTTA -> 15.82
            Material.RED_TERRACOTTA -> 15.06
            Material.BLACK_TERRACOTTA -> 15.41
            Material.WHITE_STAINED_GLASS_PANE -> .45
            Material.ORANGE_STAINED_GLASS_PANE -> .36
            Material.MAGENTA_STAINED_GLASS_PANE -> .41
            Material.LIGHT_BLUE_STAINED_GLASS_PANE -> .45
            Material.YELLOW_STAINED_GLASS_PANE -> 0.36
            Material.LIME_STAINED_GLASS_PANE -> .61
            Material.PINK_STAINED_GLASS_PANE -> .41
            Material.GRAY_STAINED_GLASS_PANE -> .5
            Material.LIGHT_GRAY_STAINED_GLASS_PANE -> .47
            Material.CYAN_STAINED_GLASS_PANE -> .61
            Material.PURPLE_STAINED_GLASS_PANE -> .41
            Material.BLUE_STAINED_GLASS_PANE -> .45
            Material.BROWN_STAINED_GLASS_PANE -> .67
            Material.GREEN_STAINED_GLASS_PANE -> .74
            Material.RED_STAINED_GLASS_PANE -> .36
            Material.BLACK_STAINED_GLASS_PANE -> .53
            Material.ACACIA_WOOD, Material.DARK_OAK_WOOD -> 14.0
            Material.ACACIA_STAIRS, Material.DARK_OAK_STAIRS -> 4.13
            Material.SLIME_BLOCK -> 94.50
            Material.IRON_TRAPDOOR -> 70.56
            Material.PRISMARINE -> 21.0
            Material.PRISMARINE_BRICKS -> 47.25
            Material.DARK_PRISMARINE -> 45.31
            Material.PRISMARINE_STAIRS -> 33.08
            Material.PRISMARINE_BRICK_STAIRS -> 74.42
            Material.DARK_PRISMARINE_STAIRS -> 71.36
            Material.PRISMARINE_SLAB -> 11.03
            Material.PRISMARINE_BRICK_SLAB -> 24.81
            Material.DARK_PRISMARINE_SLAB -> 23.79
            Material.SEA_LANTERN -> 60.38
            Material.HAY_BLOCK -> 28.35
            Material.WHITE_CARPET -> 10.5
            Material.ORANGE_CARPET -> 11.1
            Material.MAGENTA_CARPET -> 11.2
            Material.LIGHT_BLUE_CARPET -> 11.28
            Material.YELLOW_CARPET -> 11.09
            Material.LIME_CARPET -> 11.59
            Material.PINK_CARPET -> 11.19
            Material.GRAY_CARPET -> 11.37
            Material.LIGHT_GRAY_CARPET -> 11.32
            Material.CYAN_CARPET -> 11.59
            Material.PURPLE_CARPET -> 11.19
            Material.BLUE_CARPET -> 11.27
            Material.BROWN_CARPET -> 11.71
            Material.GREEN_CARPET -> 11.85
            Material.RED_CARPET -> 11.09
            Material.BLACK_CARPET -> 11.44
            Material.TERRACOTTA -> 14.28
            Material.COAL_BLOCK -> 75.60
            Material.PACKED_ICE -> 66.15
            Material.RED_SANDSTONE -> 6.3
            Material.CHISELED_RED_SANDSTONE -> 7.29
            Material.CUT_RED_SANDSTONE -> 6.62
            Material.SMOOTH_RED_SANDSTONE -> 7.67
            Material.RED_SANDSTONE_STAIRS -> 9.92
            Material.RED_SANDSTONE_SLAB -> 3.47
            Material.SPRUCE_FENCE_GATE, Material.BIRCH_FENCE_GATE, Material.JUNGLE_FENCE_GATE, Material.DARK_OAK_FENCE_GATE, Material.ACACIA_FENCE_GATE -> 11.3
            Material.SPRUCE_FENCE, Material.BIRCH_FENCE, Material.JUNGLE_FENCE, Material.DARK_OAK_FENCE, Material.ACACIA_FENCE -> 4.64
            Material.END_ROD -> 27.9
            Material.PURPUR_BLOCK -> 26.46
            Material.PURPUR_PILLAR -> 29.17
            Material.PURPUR_STAIRS -> 41.67
            Material.PURPUR_SLAB -> 13.89
            Material.END_STONE_BRICKS -> 30.63
            Material.MAGMA_BLOCK -> 91.88
            Material.NETHER_WART_BLOCK -> 141.75
            Material.RED_NETHER_BRICKS -> 34.65
            Material.BONE_BLOCK -> 16.54
            Material.OBSERVER -> 30.98
            Material.SHULKER_BOX -> 75.65
            Material.WHITE_SHULKER_BOX -> 81.27
            Material.ORANGE_SHULKER_BOX -> 80.01
            Material.MAGENTA_SHULKER_BOX -> 80.80
            Material.LIGHT_BLUE_SHULKER_BOX -> 81.46
            Material.YELLOW_SHULKER_BOX -> 79.99
            Material.LIME_SHULKER_BOX -> 83.92
            Material.PINK_SHULKER_BOX -> 80.74
            Material.GRAY_SHULKER_BOX -> 82.18
            Material.LIGHT_GRAY_SHULKER_BOX -> 81.82
            Material.CYAN_SHULKER_BOX -> 83.92
            Material.PURPLE_SHULKER_BOX -> 80.74
            Material.BLUE_SHULKER_BOX -> 81.36
            Material.BROWN_SHULKER_BOX -> 84.95
            Material.GREEN_SHULKER_BOX -> 86.05
            Material.RED_SHULKER_BOX -> 79.99
            Material.BLACK_SHULKER_BOX -> 82.74
            Material.WHITE_GLAZED_TERRACOTTA -> 17.01
            Material.ORANGE_GLAZED_TERRACOTTA -> 16.97
            Material.MAGENTA_GLAZED_TERRACOTTA -> 17.0
            Material.LIGHT_BLUE_GLAZED_TERRACOTTA -> 21.66
            Material.YELLOW_GLAZED_TERRACOTTA -> 16.87
            Material.LIME_GLAZED_TERRACOTTA -> 16.99
            Material.PINK_GLAZED_TERRACOTTA -> 19.47
            Material.GRAY_GLAZED_TERRACOTTA -> 20.1
            Material.LIGHT_GRAY_GLAZED_TERRACOTTA -> 18.54
            Material.CYAN_GLAZED_TERRACOTTA -> 16.87
            Material.PURPLE_GLAZED_TERRACOTTA -> 17.48
            Material.BLUE_GLAZED_TERRACOTTA -> 17.05
            Material.BROWN_GLAZED_TERRACOTTA -> 17.52
            Material.GREEN_GLAZED_TERRACOTTA -> 17.66
            Material.RED_GLAZED_TERRACOTTA -> 16.87
            Material.BLACK_GLAZED_TERRACOTTA -> 17.23
            Material.WHITE_CONCRETE -> .79
            Material.ORANGE_CONCRETE -> .63
            Material.MAGENTA_CONCRETE -> .73
            Material.LIGHT_BLUE_CONCRETE -> .82
            Material.YELLOW_CONCRETE -> .62
            Material.LIME_CONCRETE -> 1.14
            Material.PINK_CONCRETE -> .72
            Material.GRAY_CONCRETE -> .91
            Material.LIGHT_GRAY_CONCRETE -> .86
            Material.CYAN_CONCRETE -> 1.14
            Material.PURPLE_CONCRETE -> .72
            Material.BLUE_CONCRETE -> .8
            Material.BROWN_CONCRETE -> 1.27
            Material.GREEN_CONCRETE -> 1.42
            Material.RED_CONCRETE -> .62
            Material.BLACK_CONCRETE -> .99
            Material.WHITE_CONCRETE_POWDER -> .75
            Material.ORANGE_CONCRETE_POWDER -> .6
            Material.MAGENTA_CONCRETE_POWDER -> 0.7
            Material.LIGHT_BLUE_CONCRETE_POWDER -> .78
            Material.YELLOW_CONCRETE_POWDER -> .59
            Material.LIME_CONCRETE_POWDER -> 1.09
            Material.PINK_CONCRETE_POWDER -> .69
            Material.GRAY_CONCRETE_POWDER -> .87
            Material.LIGHT_GRAY_CONCRETE_POWDER -> .82
            Material.CYAN_CONCRETE_POWDER -> 1.09
            Material.PURPLE_CONCRETE_POWDER -> .69
            Material.BLUE_CONCRETE_POWDER -> .77
            Material.BROWN_CONCRETE_POWDER -> 1.21
            Material.GREEN_CONCRETE_POWDER -> 1.35
            Material.RED_CONCRETE_POWDER -> .59
            Material.BLACK_CONCRETE_POWDER -> .94
            Material.IRON_SHOVEL -> 20.53
            Material.IRON_PICKAXE, Material.IRON_AXE -> 55.81
            Material.FLINT_AND_STEEL -> 18.17
            Material.BOW -> 9.07
            Material.ARROW -> .62
            Material.CHARCOAL -> 11.55
            Material.IRON_INGOT -> 16.8
            Material.GOLD_INGOT -> .0
            Material.IRON_SWORD -> 36.73
            Material.WOODEN_SWORD -> 6.96
            Material.WOODEN_PICKAXE, Material.WOODEN_AXE -> 11.16
            Material.STONE_SWORD -> 3.55
            Material.STONE_SHOVEL -> 3.94
            Material.STONE_PICKAXE, Material.STONE_AXE -> 6.04
            Material.DIAMOND_SWORD -> .0
            Material.DIAMOND_SHOVEL -> 527.89
            Material.DIAMOND_PICKAXE, Material.DIAMOND_AXE -> .0
            Material.STICK -> 1.38
            Material.BOWL -> 2.07
            Material.MUSHROOM_STEW -> 4.27
            Material.GOLDEN_SWORD -> .0
            Material.GOLDEN_SHOVEL -> 59.12
            Material.GOLDEN_PICKAXE, Material.GOLDEN_AXE -> 171.58
            Material.WOODEN_HOE -> 8.41
            Material.STONE_HOE -> 4.99
            Material.IRON_HOE -> 38.17
            Material.DIAMOND_HOE -> 1052.89
            Material.GOLDEN_HOE -> 115.35
            Material.BREAD -> 9.45
            Material.LEATHER_HELMET -> 39.38
            Material.LEATHER_CHESTPLATE -> 63.0
            Material.LEATHER_LEGGINGS -> 55.13
            Material.LEATHER_BOOTS -> 31.50
            Material.CHAINMAIL_HELMET -> 300.0
            Material.CHAINMAIL_CHESTPLATE -> 650.0
            Material.CHAINMAIL_LEGGINGS -> 500.0
            Material.CHAINMAIL_BOOTS -> 300.0
            Material.IRON_HELMET -> 88.0
            Material.IRON_CHESTPLATE -> 141.12
            Material.IRON_LEGGINGS -> 123.48
            Material.IRON_BOOTS -> 70.56
            Material.DIAMOND_HELMET -> .0
            Material.DIAMOND_CHESTPLATE -> .0
            Material.DIAMOND_LEGGINGS -> .0
            Material.DIAMOND_BOOTS -> .0
            Material.GOLDEN_HELMET -> 281.14
            Material.GOLDEN_CHESTPLATE -> 449.82
            Material.GOLDEN_LEGGINGS -> 393.59
            Material.GOLDEN_BOOTS -> 224.91
            Material.COOKED_PORKCHOP -> 5.25
            Material.PAINTING -> 16.83
            Material.GOLDEN_APPLE -> 55.63
            Material.ENCHANTED_GOLDEN_APPLE -> 100000.0
            Material.OAK_SIGN, Material.SPRUCE_SIGN, Material.BIRCH_SIGN, Material.JUNGLE_SIGN, Material.ACACIA_SIGN, Material.DARK_OAK_SIGN -> 5.99
            Material.OAK_DOOR -> 5.51
            Material.BUCKET -> 52.92
            Material.WATER_BUCKET -> .0
            Material.LAVA_BUCKET -> .0
            Material.MINECART -> 88.20
            Material.IRON_DOOR -> 35.28
            Material.OAK_BOAT -> 13.78
            Material.MILK_BUCKET -> 56.09
            Material.BRICK -> 4.20
            Material.PAPER -> 0.53
            Material.BOOK -> 9.53
            Material.CHEST_MINECART -> 115.76
            Material.FURNACE_MINECART -> 101.43
            Material.COMPASS -> 144.27
            Material.FISHING_ROD -> 7.49
            Material.CLOCK -> 228.06
            Material.COOKED_SALMON -> 4.46
            Material.RED_DYE -> 0.53
            Material.GREEN_DYE -> 6.30
            Material.PURPLE_DYE -> 1.24
            Material.CYAN_DYE -> 4.27
            Material.LIGHT_GRAY_DYE -> 2.27
            Material.GRAY_DYE -> 2.62
            Material.PINK_DYE -> 1.24
            Material.LIME_DYE -> 4.27
            Material.YELLOW_DYE -> 0.53
            Material.LIGHT_BLUE_DYE -> 1.93
            Material.MAGENTA_DYE -> 1.30
            Material.ORANGE_DYE -> 0.55
            Material.BONE_MEAL -> 1.75
            Material.BLACK_DYE -> 3.15
            Material.BROWN_DYE -> 5.25
            Material.BLUE_DYE, Material.WHITE_DYE -> 1.84
            Material.SUGAR -> .53
            Material.CAKE -> 12.65
            Material.WHITE_BED -> 24.02
            Material.REPEATER -> 19.42
            Material.COOKIE -> 1.44
            Material.SHEARS -> 35.28
            Material.PUMPKIN_SEEDS -> 1.31
            Material.MELON_SEEDS -> 1.58
            Material.COOKED_BEEF -> 6.30
            Material.COOKED_CHICKEN -> 3.68
            Material.GOLD_NUGGET -> .0
            Material.GLASS_BOTTLE -> 1.65
            Material.FERMENTED_SPIDER_EYE -> 4.75
            Material.BLAZE_POWDER -> 52.50
            Material.MAGMA_CREAM -> 21.88
            Material.BREWING_STAND -> 108.15
            Material.CAULDRON -> 123.48
            Material.ENDER_EYE -> 107.63
            Material.GLISTERING_MELON_SLICE -> 8.13
            Material.FIRE_CHARGE -> 74.03
            Material.WRITABLE_BOOK -> 13.68
            Material.ITEM_FRAME -> 19.45
            Material.FLOWER_POT -> 13.23
            Material.BAKED_POTATO -> 1.58
            Material.MAP -> 155.89
            Material.GOLDEN_CARROT -> 53.27
            Material.CARROT_ON_A_STICK -> 8.65
            Material.PUMPKIN_PIE -> 6.33
            Material.FIREWORK_ROCKET -> 11.05
            Material.COMPARATOR -> 272.70
            Material.NETHER_BRICK -> 6.30
            Material.TNT_MINECART -> 149.94
            Material.HOPPER_MINECART -> 209.53
            Material.COOKED_RABBIT -> 2.63
            Material.RABBIT_STEW -> 8.42
            Material.ARMOR_STAND -> 9.23
            Material.LEATHER_HORSE_ARMOR -> 39.38
            Material.LEAD -> 8.40
            Material.COOKED_MUTTON -> 2.10
            Material.BLACK_BANNER -> 55.36
            Material.RED_BANNER -> 37.99
            Material.GREEN_BANNER -> 76.20
            Material.BROWN_BANNER -> 69.25
            Material.BLUE_BANNER -> 46.68
            Material.PURPLE_BANNER -> 69.25
            Material.CYAN_BANNER -> 62.78
            Material.LIGHT_GRAY_BANNER -> 49.56
            Material.GRAY_BANNER -> 51.84
            Material.PINK_BANNER -> 42.73
            Material.LIME_BANNER -> 62.78
            Material.YELLOW_BANNER -> 37.99
            Material.LIGHT_BLUE_BANNER -> 47.28
            Material.MAGENTA_BANNER -> 43.14
            Material.ORANGE_BANNER -> 38.17
            Material.WHITE_BANNER -> 32.95
            Material.FLOWER_BANNER_PATTERN -> 2.13
            Material.CREEPER_BANNER_PATTERN -> 263.05
            Material.SKULL_BANNER_PATTERN -> 53.05
            Material.MOJANG_BANNER_PATTERN -> 10000.0
            Material.GLOBE_BANNER_PATTERN -> 400.0
            Material.PIGLIN_BANNER_PATTERN -> 500.0
            Material.END_CRYSTAL -> 203.33
            Material.SPRUCE_DOOR, Material.BIRCH_DOOR, Material.JUNGLE_DOOR, Material.ACACIA_DOOR, Material.DARK_OAK_DOOR -> 5.51
            Material.CHORUS_FRUIT -> 5.0
            Material.BEETROOT -> .6
            Material.BEETROOT_SOUP -> 5.95
            Material.SPECTRAL_ARROW -> 17.13
            Material.SPRUCE_BOAT, Material.BIRCH_BOAT, Material.JUNGLE_BOAT, Material.ACACIA_BOAT, Material.DARK_OAK_BOAT -> 13.78
            Material.DRIED_KELP -> 2.10
            Material.DRIED_KELP_BLOCK -> 19.85
            Material.COD_BUCKET -> 58.72
            Material.SALMON_BUCKET -> 58.98
            Material.TROPICAL_FISH_BUCKET -> 57.14
            Material.PUFFERFISH_BUCKET -> 60.82
            Material.BLUE_ICE -> 625.12
            Material.CONDUIT -> 1470.0
            Material.LOOM -> 8.66
            Material.CROSSBOW -> 36.60
            Material.BARREL -> 19.43
            Material.BLAST_FURNACE -> 107.27
            Material.SMOKER -> 19.85
            Material.CARTOGRAPHY_TABLE -> 12.13
            Material.FLETCHING_TABLE -> 12.08
            Material.SMITHING_TABLE -> 46.31
            Material.STONECUTTER -> 24.26
            Material.GRINDSTONE -> 11.01
            Material.LECTERN -> 54.67
            Material.SCAFFOLDING -> 2.21
            Material.LANTERN -> 19.38
            Material.CAMPFIRE -> 21.01
            Material.COMPOSTER -> 10.13
            Material.SOUL_CAMPFIRE -> 50.06
            Material.BEEHIVE -> 19.69
            Material.HONEY_BOTTLE -> 2.79
            Material.HONEY_BLOCK, Material.HONEYCOMB_BLOCK -> 4.20
            Material.POLISHED_BASALT -> 10.5
            Material.CRIMSON_HYPHAE, Material.WARPED_HYPHAE -> 17.5
            Material.CRIMSON_PLANKS, Material.WARPED_PLANKS -> 3.28
            Material.CRIMSON_SLAB, Material.WARPED_SLAB -> 1.72
            Material.CRIMSON_STAIRS, Material.WARPED_STAIRS -> 5.17
            Material.CRIMSON_FENCE, Material.WARPED_FENCE -> 5.56
            Material.CRIMSON_FENCE_GATE, Material.WARPED_FENCE_GATE -> 12.68
            Material.CRIMSON_PRESSURE_PLATE, Material.WARPED_PRESSURE_PLATE -> 6.89
            Material.CRIMSON_BUTTON, Material.WARPED_BUTTON -> 3.45
            Material.CRIMSON_DOOR, Material.WARPED_DOOR -> 6.89
            Material.CRIMSON_TRAPDOOR, Material.WARPED_TRAPDOOR -> 10.34
            Material.CRIMSON_SIGN, Material.WARPED_SIGN -> 7.37
            Material.SOUL_TORCH -> 11.95
            Material.SOUL_LANTERN -> 29.34
            Material.TARGET -> 122.22
            Material.RESPAWN_ANCHOR -> 735.84
            Material.LODESTONE -> 3502.56
            Material.WARPED_FUNGUS_ON_A_STICK -> 9.18
            Material.POLISHED_BLACKSTONE -> 1.58
            Material.CHISELED_POLISHED_BLACKSTONE -> 1.74
            Material.BLACKSTONE_SLAB -> .79
            Material.POLISHED_BLACKSTONE_SLAB -> .83
            Material.POLISHED_BLACKSTONE_BRICK_SLAB -> .87
            Material.POLISHED_BLACKSTONE_BRICKS -> 1.65
            Material.CRACKED_POLISHED_BLACKSTONE_BRICKS -> 2.79
            Material.BLACKSTONE_STAIRS -> 2.36
            Material.POLISHED_BLACKSTONE_STAIRS -> 2.48
            Material.POLISHED_BLACKSTONE_BRICK_STAIRS -> 2.6
            Material.BLACKSTONE_WALL -> 1.58
            Material.POLISHED_BLACKSTONE_WALL -> 1.65
            Material.POLISHED_BLACKSTONE_BRICK_WALL -> 1.74
            Material.POLISHED_BLACKSTONE_BUTTON -> 1.65
            Material.POLISHED_BLACKSTONE_PRESSURE_PLATE -> 3.31
            Material.CHAIN -> 21.84
            Material.WHITE_WOOL -> 15.0
            Material.DANDELION, Material.POPPY -> 1.0
            Material.BLUE_ORCHID -> 2.5
            Material.ALLIUM -> 5.0

            Material.AZURE_BLUET, Material.RED_TULIP, Material.ORANGE_TULIP, Material.WHITE_TULIP,
            Material.PINK_TULIP, Material.CORNFLOWER, Material.LILY_OF_THE_VALLEY -> 1.75
            Material.OXEYE_DAISY -> 1.5
            Material.WITHER_ROSE -> 25.0
            Material.BROWN_MUSHROOM, Material.RED_MUSHROOM -> 1.0
            Material.ICE -> 7.0
            Material.CACTUS -> 5.0
            Material.CARVED_PUMPKIN -> 3.0
            Material.NETHERRACK -> 5.0
            Material.SOUL_SAND -> 10.0
            Material.MUSHROOM_STEM -> 2.0
            Material.VINE -> 5.0
            Material.MYCELIUM -> 10.0
            Material.LILY_PAD -> 3.0
            Material.END_STONE -> 10.0
            Material.ACACIA_LEAVES, Material.DARK_OAK_LEAVES -> 2.0
            Material.ACACIA_LOG, Material.DARK_OAK_LOG, Material.STRIPPED_ACACIA_LOG,
            Material.STRIPPED_DARK_OAK_LOG, Material.STRIPPED_ACACIA_WOOD, Material.STRIPPED_DARK_OAK_WOOD -> 10.0

            Material.SUNFLOWER -> 3.0
            Material.LILAC -> 2.5
            Material.ROSE_BUSH, Material.PEONY -> 1.0
            Material.CHORUS_FLOWER -> 25.0
            Material.APPLE -> 3.0
            Material.STRING -> 1.5
            Material.FEATHER -> .5
            Material.GUNPOWDER -> 10.0
            Material.WHEAT -> 3.0
            Material.SADDLE -> 500.0
            Material.REDSTONE -> 3.0
            Material.SNOWBALL -> 1.0
            Material.LEATHER -> 7.5
            Material.CLAY_BALL -> 3.0
            Material.SUGAR_CANE -> .5
            Material.COCOA_BEANS -> 5.0
            Material.BONE -> 5.0
            Material.MELON_SLICE -> 1.5
            Material.BLAZE_ROD -> 100.0
            Material.GHAST_TEAR -> 75.0
            Material.SPIDER_EYE -> 3.0
            Material.CARROT -> .75
            Material.POTATO -> .5
            Material.ZOMBIE_HEAD, Material.SKELETON_SKULL, Material.PLAYER_HEAD, Material.CREEPER_HEAD -> 10000.0
            Material.QUARTZ -> 17.5
            Material.ELYTRA -> 5000.0
            Material.TOTEM_OF_UNDYING, Material.TRIDENT -> 500.0
            Material.BELL -> 1800.0
            Material.SUSPICIOUS_STEW -> 10.0
            Material.SWEET_BERRIES -> 0.5
            Material.BASALT -> 10.0
            Material.CRIMSON_FUNGUS, Material.WARPED_FUNGUS -> 1.25
            Material.CRIMSON_STEM, Material.WARPED_STEM,
            Material.STRIPPED_CRIMSON_STEM, Material.STRIPPED_WARPED_STEM,
            Material.STRIPPED_CRIMSON_HYPHAE, Material.STRIPPED_WARPED_HYPHAE -> 12.5

            Material.SOUL_SOIL -> 10.0
            Material.TWISTING_VINES, Material.WEEPING_VINES -> 5.0
            Material.CRYING_OBSIDIAN -> 100.0
            Material.BLACKSTONE -> 1.5
            Material.GILDED_BLACKSTONE -> 25.0
            Material.OAK_LOG, Material.SPRUCE_LOG, Material.BIRCH_LOG,
            Material.JUNGLE_LOG, Material.STRIPPED_OAK_LOG, Material.STRIPPED_SPRUCE_LOG,
            Material.STRIPPED_BIRCH_LOG, Material.STRIPPED_JUNGLE_LOG, Material.STRIPPED_OAK_WOOD,
            Material.STRIPPED_SPRUCE_WOOD, Material.STRIPPED_BIRCH_WOOD, Material.STRIPPED_JUNGLE_WOOD -> 10.0

            Material.OAK_LEAVES, Material.SPRUCE_LEAVES, Material.BIRCH_LEAVES, Material.JUNGLE_LEAVES -> 2.0
            Material.WET_SPONGE -> 10.0
            Material.LAPIS_ORE -> 20.0
            Material.REDSTONE_ORE -> 15.0
            Material.IRON_ORE -> 15.0
            Material.SAND -> .5
            Material.RED_SAND -> 1.5
            Material.GRAVEL -> .5
            Material.GOLD_ORE, Material.NETHER_GOLD_ORE -> 50.0
            Material.COAL_ORE -> 10.0
            Material.NETHER_QUARTZ_ORE -> 20.0
            Material.ENDER_PEARL -> 50.0
            Material.ROTTEN_FLESH -> 1.0
            Material.NAME_TAG -> 50.0
            Material.MUSIC_DISC_13, Material.MUSIC_DISC_CAT,
            Material.MUSIC_DISC_BLOCKS, Material.MUSIC_DISC_CHIRP,
            Material.MUSIC_DISC_MALL, Material.MUSIC_DISC_MELLOHI,
            Material.MUSIC_DISC_STAL, Material.MUSIC_DISC_STRAD,
            Material.MUSIC_DISC_WARD, Material.MUSIC_DISC_11, Material.MUSIC_DISC_WAIT -> 100.0

            Material.MUSIC_DISC_PIGSTEP -> 250.0
            else -> .0
        }
    }
}