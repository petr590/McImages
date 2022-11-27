package mcimages;

public class Versions {

	public static final int
			NONE = 0,
			
			V1_13   = 13_00,
			V1_13_1 = 13_01,
			V1_13_2 = 13_02,
			
			V1_14   = 14_00,
			V1_14_1 = 14_01,
			V1_14_2 = 14_02,
			V1_14_3 = 14_03,
			V1_14_4 = 14_04,
			
			V1_15   = 15_00,
			V1_15_1 = 15_01,
			V1_15_2 = 15_02,
			
			V1_16   = 16_00,
			V1_16_1 = 16_01,
			V1_16_2 = 16_02,
			V1_16_3 = 16_03,
			V1_16_4 = 16_04,
			V1_16_5 = 16_05,
			
			V1_17   = 17_00,
			V1_17_1 = 17_01,
			
			V1_18   = 18_00,
			V1_18_1 = 18_01,
			V1_18_2 = 18_02,
			
			V1_19   = 19_00,
			V1_19_1 = 19_01,
			V1_19_2 = 19_02;
	
	
	public static int majorVersion(int version) {
		return version / 100;
	}
	
	public static int packFormatByVersion(int version) {
		switch(majorVersion(version)) {
			case 13, 14: return 4;
			case 15:     return 5;
			case 16:     return version <= V1_16_1 ? 5 : 6;
			case 17:     return 7;
			case 18:     return version <= V1_18_1 ? 8 : 9;
			case 19:     return 10;
			default:     return version > V1_19 ? 10 : 1;
		}
	}
}