package com.enjoyor.soa.traffic.rest.solr.bean;

import org.apache.solr.client.solrj.beans.Field;

import java.util.ArrayList;

/**
 * 现场单机一次识别卡口过车信息 (直连九江单机测试 用)
 * 用于SolrDocument 和 JavaBean对象相互转换
 *
 */
public class PassInfoPojo {

	/**
	 * 序号
	 */
	@Field
	private String id;
	/**
	 * 卡口编号
	 */
	@Field
	private String bayonet_id;
	/**
	 * 卡口名称
	 */
	@Field
	private String bayonet_name;
	/**
	 * 方向编号
	 */
	@Field
	private String direction;
	/**
	 * 方向名称
	 */
	@Field
	private String dir_name;
	/**
	 * 车道号
	 */
	@Field
	private String lane_id;
	/**
	 * 过车时间
	 */
	@Field
	private String pass_time;
	/**
	 * 号牌号码
	 */
	@Field
	private String vehicle_plate;
	/**
	 * 号牌类型
	 */
	@Field
	private String vehicle_plate_type;
	/**
	 * 号牌类型名称
	 */
	@Field
	private String vehicle_plate_type_name;
	/**
	 * 号牌颜色
	 */
	@Field
	private String vehicle_plate_color;
	/**
	 * 号牌颜色名称
	 */
	@Field
	private String vehicle_plate_color_name;
	/**
	 * 车辆类型
	 */
	@Field
	private String vehicle_type;
	/**
	 * 车辆类型名称
	 */
	@Field
	private String vehicle_type_name;
	/**
	 * 车辆颜色
	 */
	@Field
	private String vehicle_color;
	/**
	 * 车辆颜色名称
	 */
	@Field
	private String vehicle_color_name;
	/**
	 * 车辆品牌
	 */
	@Field
	private String vehicle_brand;

	/**
	 * 车辆速度
	 */
	@Field
	private String speed;
	/**
	 * 最小行驶速度
	 */
	@Field
	private String min_speed;
	/**
	 * 最大行驶速度
	 */
	@Field
	private String max_speed;

	/**
	 * 数据来源
	 */
	@Field
	private String data_source;

	/**
	 * 数据来源名称
	 */
	@Field
	private String data_source_name;
	/**
	 * 采集方式
	 */
	@Field
	private String collect_type;
	/**
	 * 采集方式名称
	 */
	@Field
	private String collect_type_name;
	/**
	 * 记录时间
	 */
	@Field
	private String record_time;
	/**
	 * 图片路径1
	 */
	@Field
	private String pic_url1;
	/**
	 * 图片路径2
	 */
	@Field
	private String pic_url2;
	/**
	 * 图片路径3
	 */
	@Field
	private String pic_url3;
	/**
	 * 图片路径4
	 */
	@Field
	private String pic_url4;
	/**
	 * 视频路径
	 */
	@Field
	private String video_url;

	/**
	 * 车道数  WL
	 */
	@Field
	private String laneNumber;
	/**
	 * 车辆长度  WL
	 */
	@Field
	private String vehLength;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBayonet_id() {
		return bayonet_id;
	}

	public void setBayonet_id(String bayonet_id) {
		this.bayonet_id = bayonet_id;
	}

	public String getBayonet_name() {
		return bayonet_name;
	}

	public void setBayonet_name(String bayonet_name) {
		this.bayonet_name = bayonet_name;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getDir_name() {
		return dir_name;
	}

	public void setDir_name(String dir_name) {
		this.dir_name = dir_name;
	}

	public String getLane_id() {
		return lane_id;
	}

	public void setLane_id(String lane_id) {
		this.lane_id = lane_id;
	}

	public String getPass_time() {
		return pass_time;
	}

	public void setPass_time(String pass_time) {
		this.pass_time = pass_time;
	}

	public String getVehicle_plate() {
		return vehicle_plate;
	}

	public void setVehicle_plate(String vehicle_plate) {
		this.vehicle_plate = vehicle_plate;
	}

	public String getVehicle_plate_type() {
		return vehicle_plate_type;
	}

	public void setVehicle_plate_type(String vehicle_plate_type) {
		this.vehicle_plate_type = vehicle_plate_type;
	}

	public String getVehicle_plate_type_name() {
		return vehicle_plate_type_name;
	}

	public void setVehicle_plate_type_name(String vehicle_plate_type_name) {
		this.vehicle_plate_type_name = vehicle_plate_type_name;
	}

	public String getVehicle_plate_color() {
		return vehicle_plate_color;
	}

	public void setVehicle_plate_color(String vehicle_plate_color) {
		this.vehicle_plate_color = vehicle_plate_color;
	}

	public String getVehicle_plate_color_name() {
		return vehicle_plate_color_name;
	}

	public void setVehicle_plate_color_name(String vehicle_plate_color_name) {
		this.vehicle_plate_color_name = vehicle_plate_color_name;
	}

	public String getVehicle_type() {
		return vehicle_type;
	}

	public void setVehicle_type(String vehicle_type) {
		this.vehicle_type = vehicle_type;
	}

	public String getVehicle_type_name() {
		return vehicle_type_name;
	}

	public void setVehicle_type_name(String vehicle_type_name) {
		this.vehicle_type_name = vehicle_type_name;
	}

	public String getVehicle_color() {
		return vehicle_color;
	}

	public void setVehicle_color(String vehicle_color) {
		this.vehicle_color = vehicle_color;
	}

	public String getVehicle_color_name() {
		return vehicle_color_name;
	}

	public void setVehicle_color_name(String vehicle_color_name) {
		this.vehicle_color_name = vehicle_color_name;
	}

	public String getVehicle_brand() {
		return vehicle_brand;
	}

	public void setVehicle_brand(String vehicle_brand) {
		this.vehicle_brand = vehicle_brand;
	}

	public String getSpeed() {
		return speed;
	}

	public void setSpeed(String speed) {
		this.speed = speed;
	}

	public String getMin_speed() {
		return min_speed;
	}

	public void setMin_speed(String min_speed) {
		this.min_speed = min_speed;
	}

	public String getMax_speed() {
		return max_speed;
	}

	public void setMax_speed(String max_speed) {
		this.max_speed = max_speed;
	}

	public String getData_source() {
		return data_source;
	}

	public void setData_source(String data_source) {
		this.data_source = data_source;
	}

	public String getData_source_name() {
		return data_source_name;
	}

	public void setData_source_name(String data_source_name) {
		this.data_source_name = data_source_name;
	}

	public String getCollect_type() {
		return collect_type;
	}

	public void setCollect_type(String collect_type) {
		this.collect_type = collect_type;
	}

	public String getCollect_type_name() {
		return collect_type_name;
	}

	public void setCollect_type_name(String collect_type_name) {
		this.collect_type_name = collect_type_name;
	}

	public String getRecord_time() {
		return record_time;
	}

	public void setRecord_time(String record_time) {
		this.record_time = record_time;
	}

	public String getPic_url1() {
		return pic_url1;
	}

	public void setPic_url1(String pic_url1) {
		this.pic_url1 = pic_url1;
	}

	public String getPic_url2() {
		return pic_url2;
	}

	public void setPic_url2(String pic_url2) {
		this.pic_url2 = pic_url2;
	}

	public String getPic_url3() {
		return pic_url3;
	}

	public void setPic_url3(String pic_url3) {
		this.pic_url3 = pic_url3;
	}

	public String getPic_url4() {
		return pic_url4;
	}

	public void setPic_url4(String pic_url4) {
		this.pic_url4 = pic_url4;
	}

	public String getVideo_url() {
		return video_url;
	}

	public void setVideo_url(String video_url) {
		this.video_url = video_url;
	}

	public String getLaneNumber() {
		return laneNumber;
	}

	public void setLaneNumber(String laneNumber) {
		this.laneNumber = laneNumber;
	}

	public String getVehLength() {
		return vehLength;
	}

	public void setVehLength(String vehLength) {
		this.vehLength = vehLength;
	}
}
