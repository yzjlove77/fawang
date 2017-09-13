package com.android.wx.french.model;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class CourtNamebean implements Serializable{

	public List<CourtName> fy_data;

	public class CourtName{


		public String fybm;
		public String fybm1;
		public String fybm2;
		public String sjfybm1;
		public String xzqhdm;
		public String fymc;
		public String jc;
		public String zhjc;
		public String px;
		public String zt;
		public List<FaTing> son;

		public class FaTing{
			public String id;
			public String ls_fybm;
			public String ls_ftbm;
			public String ls_ftmc;
			public String ls_ftjc;
			public String ls_state;
		}

	}
}
