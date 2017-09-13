package com.android.wx.french.model;


import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class GetNoticebean implements Serializable{

	public List<GetNotice> data;

	public class GetNotice{

		public String fy_name;
		public String fybm1;
		public String dqdm;
		public List<CourtNamebean.CourtName> fy_data;
		public List<CourtNamebean.CourtName> getFy_data() {
			return fy_data;
		}
		public void setFy_data(List<CourtNamebean.CourtName> fy_data) {
			this.fy_data = fy_data;
		}
		public String falg;
		public String getFalg() {
			return falg;
		}
		public void setFalg(String falg) {
			this.falg = falg;
		}
	}
}
