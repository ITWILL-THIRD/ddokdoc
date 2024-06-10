package com.todoc.hospital;

import java.sql.Time;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class HospitalVO {
   //hospital 테이블
   private int hosIdx;
   private String hosId;
   private String hosPw;
   private String hosName;
   private String hosPhone;
   private String animal;
   private double score;
   private String condition;
   //hostime 테이블
   private Time openTime;
   private Time closeTime;
   private Time lunchTime;
   private Time endLunchTime;
   //hostime time 포맷타입
   private String formatOpenTime;
   private String formatCloseTime;
   // +토,일요일 시간 컬럼4개 추가
   private Time satOpenTime;
   private Time satCloseTime;
   private Time satLunchTime;
   private Time satEndLunchTime;
   private Time sunOpenTime;
   private Time sunCloseTime;
   private Time sunLunchTime;
   private Time sunEndLunchTime;
   //hosaddress 테이블
   private String addressName;
   private String roadAddressName;
   private String detailAddress;
   private String addx;
   private String addy;
   private String sido;
   private String sigungu;
   
   //병원 검색 조건 처리용
   @JsonIgnore
   private String searchCondition;
   @JsonIgnore
   private String searchKeyword;
   
	public int getHosIdx() {
		return hosIdx;
	}
	public void setHosIdx(int hosIdx) {
		this.hosIdx = hosIdx;
	}
	public String getHosId() {
		return hosId;
	}
	public void setHosId(String hosId) {
		this.hosId = hosId;
	}
	public String getHosPw() {
		return hosPw;
	}
	public void setHosPw(String hosPw) {
		this.hosPw = hosPw;
	}
	public String getHosName() {
		return hosName;
	}
	public void setHosName(String hosName) {
		this.hosName = hosName;
	}
	public String getHosPhone() {
		return hosPhone;
	}
	public void setHosPhone(String hosPhone) {
		this.hosPhone = hosPhone;
	}
	public String getAnimal() {
		return animal;
	}
	public void setAnimal(String animal) {
		this.animal = animal;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public Time getOpenTime() {
		return openTime;
	}
	public void setOpenTime(Time openTime) {
		this.openTime = openTime;
	}
	public Time getCloseTime() {
		return closeTime;
	}
	public void setCloseTime(Time closeTime) {
		this.closeTime = closeTime;
	}
	public Time getLunchTime() {
		return lunchTime;
	}
	public void setLunchTime(Time lunchTime) {
		this.lunchTime = lunchTime;
	}
	public Time getEndLunchTime() {
		return endLunchTime;
	}
	public void setEndLunchTime(Time endLunchTime) {
		this.endLunchTime = endLunchTime;
	}
	public Time getSatOpenTime() {
		return satOpenTime;
	}
	public void setSatOpenTime(Time satOpenTime) {
		this.satOpenTime = satOpenTime;
	}
	public Time getSatCloseTime() {
		return satCloseTime;
	}
	public void setSatCloseTime(Time satCloseTime) {
		this.satCloseTime = satCloseTime;
	}
	public Time getSatLunchTime() {
		return satLunchTime;
	}
	public void setSatLunchTime(Time satLunchTime) {
		this.satLunchTime = satLunchTime;
	}
	public Time getSatEndLunchTime() {
		return satEndLunchTime;
	}
	public void setSatEndLunchTime(Time satEndLunchTime) {
		this.satEndLunchTime = satEndLunchTime;
	}
	public Time getSunOpenTime() {
		return sunOpenTime;
	}
	public void setSunOpenTime(Time sunOpenTime) {
		this.sunOpenTime = sunOpenTime;
	}
	public Time getSunCloseTime() {
		return sunCloseTime;
	}
	public void setSunCloseTime(Time sunCloseTime) {
		this.sunCloseTime = sunCloseTime;
	}
	public Time getSunLunchTime() {
		return sunLunchTime;
	}
	public void setSunLunchTime(Time sunLunchTime) {
		this.sunLunchTime = sunLunchTime;
	}
	public Time getSunEndLunchTime() {
		return sunEndLunchTime;
	}
	public void setSunEndLunchTime(Time sunEndLunchTime) {
		this.sunEndLunchTime = sunEndLunchTime;
	}
	public String getAddressName() {
		return addressName;
	}
	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}
	public String getRoadAddressName() {
		return roadAddressName;
	}
	public void setRoadAddressName(String roadAddressName) {
		this.roadAddressName = roadAddressName;
	}
	public String getDetailAddress() {
		return detailAddress;
	}
	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}
	public String getAddx() {
		return addx;
	}
	public void setAddx(String addx) {
		this.addx = addx;
	}
	public String getAddy() {
		return addy;
	}
	public void setAddy(String addy) {
		this.addy = addy;
	}
	public String getSido() {
		return sido;
	}
	public void setSido(String sido) {
		this.sido = sido;
	}
	public String getSigungu() {
		return sigungu;
	}
	public void setSigungu(String sigungu) {
		this.sigungu = sigungu;
	}
	public String getSearchCondition() {
		return searchCondition;
	}
	public void setSearchCondition(String searchCondition) {
		this.searchCondition = searchCondition;
	}
	public String getSearchKeyword() {
		return searchKeyword;
	}
	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}
	public String getFormatOpenTime() {
		return formatOpenTime;
	}
	public void setFormatOpenTime(String formatOpenTime) {
		this.formatOpenTime = formatOpenTime;
	}
	public String getFormatCloseTime() {
		return formatCloseTime;
	}
	public void setFormatCloseTime(String formatCloseTime) {
		this.formatCloseTime = formatCloseTime;
	}
	
	@Override
	public String toString() {
		return "HospitalVO [hosIdx=" + hosIdx + ", hosId=" + hosId + ", hosPw=" + hosPw + ", hosName=" + hosName
				+ ", hosPhone=" + hosPhone + ", animal=" + animal + ", score=" + score + ", condition=" + condition
				+ ", openTime=" + openTime + ", closeTime=" + closeTime + ", lunchTime=" + lunchTime + ", endLunchTime="
				+ endLunchTime + ", formatOpenTime=" + formatOpenTime + ", formatCloseTime=" + formatCloseTime
				+ ", satOpenTime=" + satOpenTime + ", satCloseTime=" + satCloseTime + ", satLunchTime=" + satLunchTime
				+ ", satEndLunchTime=" + satEndLunchTime + ", sunOpenTime=" + sunOpenTime + ", sunCloseTime="
				+ sunCloseTime + ", sunLunchTime=" + sunLunchTime + ", sunEndLunchTime=" + sunEndLunchTime
				+ ", addressName=" + addressName + ", roadAddressName=" + roadAddressName + ", detailAddress="
				+ detailAddress + ", addx=" + addx + ", addy=" + addy + ", sido=" + sido + ", sigungu=" + sigungu
				+ ", searchCondition=" + searchCondition + ", searchKeyword=" + searchKeyword + "]";
	}

}
