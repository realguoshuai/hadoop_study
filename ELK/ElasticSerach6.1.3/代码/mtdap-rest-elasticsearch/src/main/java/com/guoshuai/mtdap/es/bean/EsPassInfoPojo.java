package com.guoshuai.mtdap.es.bean;



import java.util.ArrayList;

/**
 * 卡口过车信息
 * 用于SolrDocument 和 JavaBean对象相互转换
 * 作为返回给上层参数
 */
public class EsPassInfoPojo {

	/*过车ID(trackId)*/
	private String id;
	private String point_id;
	private String region_id;
	private String license_type;
	private String license_color;
	private String veh_type;
	private String lane_number;
	private String veh_length;
	private String record_type;

	// 公司单机  schema string+multiValued 还是string而不是ArrayList
	/*
	private String vio_code;

    private String dept_id;*/
	//集群
	private ArrayList vio_code;

	private ArrayList dept_id;

	private String license_number;
	private String data_source;

	/*号牌智能提醒字段*/
	private String licenseNumber;

	private int speed;

	private String passing_time;
	private String speed_limit_max; //注意下
	private int speed_limit_min;
	private String veh_color;
	private String surveil_type;
	private String brand;
	private String receive_time;
	private String insert_time;
	private String pic_directory;
	private String veh_pic;
	private String character_pic;
	private String standby_pic;
	private String video_path;
	private String sr_submit_time;
	private String sr_receive_time;
	private String sr_license_number;
	private String sr_license_number_conf;
	private String sr_license_type;
	private String sr_license_type_conf;
	private String sr_license_color;
	private String sr_license_color_conf;
	private String sr_veh_type;
	private String sr_veh_type_conf;
	private String sr_veh_color;
	private String sr_veh_color_conf;
	private String sr_brand;
	private String sr_brand_conf;
	private String sr_subbrand;
	private String sr_subbrand_conf;
	private String sr_right_belt;
	private String sr_right_belt_conf;
	private String sr_left_belt;
	private String sr_left_belt_conf;
	private String sr_right_shield;
	private String sr_right_shield_conf;
	private String sr_left_shield;
	private String sr_left_shield_conf;
	private String sr_right_callup;
	private String sr_right_callup_conf;
	private String sr_left_callup;
	private String sr_left_callup_conf;
	private String sr_pendant;
	private String sr_pendant_conf;
	private String sr_yellow_label;
	private String sr_yellow_label_conf;
	private String sr_chemicals;
	private String sr_chemicals_conf;
	private String sr_damage;
	private String sr_damage_conf;
	private String brand_birth;
	private String exist_vehicle_license;

	private String direction;

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}


	public String getLicenseNumber() {
		return licenseNumber;
	}

	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPoint_id() {
		return point_id;
	}

	public void setPoint_id(String point_id) {
		this.point_id = point_id;
	}

	public String getRegion_id() {
		return region_id;
	}

	public void setRegion_id(String region_id) {
		this.region_id = region_id;
	}

	//  公司  单机
	/*public String getDept_id() {
		return dept_id;
	}

	public void setDept_id(String dept_id) {
		this.dept_id = dept_id;
	}


 	public String getVio_code() {
        return vio_code;
    }

    public void setVio_code(String vio_code) {
        this.vio_code = vio_code;
    }*/
    //集群

	public ArrayList getDept_id() {
		return dept_id;
	}

	public void setDept_id(ArrayList dept_id) {
		this.dept_id = dept_id;
	}
	public ArrayList getVio_code() {
		return vio_code;
	}

	public void setVio_code(ArrayList vio_code) {
		this.vio_code = vio_code;
	}


	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getSpeed() {
		return speed;
	}

	public int getSpeed_limit_min() {
		return speed_limit_min;
	}

	public String getSpeed_limit_max() {
		return speed_limit_max;
	}

	public void setSpeed_limit_max(String speed_limit_max) {
		this.speed_limit_max = speed_limit_max;
	}

	public void setSpeed_limit_min(int speed_limit_min) {
		this.speed_limit_min = speed_limit_min;
	}
	public String getLicense_type() {
		return license_type;
	}

	public void setLicense_type(String license_type) {
		this.license_type = license_type;
	}

	public String getLicense_color() {
		return license_color;
	}

	public void setLicense_color(String license_color) {
		this.license_color = license_color;
	}

	public String getVeh_type() {
		return veh_type;
	}

	public void setVeh_type(String veh_type) {
		this.veh_type = veh_type;
	}

	public String getLane_number() {
		return lane_number;
	}

	public void setLane_number(String lane_number) {
		this.lane_number = lane_number;
	}

	public String getVeh_length() {
		return veh_length;
	}

	public void setVeh_length(String veh_length) {
		this.veh_length = veh_length;
	}

	public String getRecord_type() {
		return record_type;
	}

	public void setRecord_type(String record_type) {
		this.record_type = record_type;
	}

	public String getLicense_number() {
		return license_number;
	}

	public void setLicense_number(String license_number) {
		this.license_number = license_number;
	}

	public String getData_source() {
		return data_source;
	}

	public void setData_source(String data_source) {
		this.data_source = data_source;
	}

	public String getPassing_time() {
		return passing_time;
	}

	public void setPassing_time(String passing_time) {
		this.passing_time = passing_time;
	}

	public String getVeh_color() {
		return veh_color;
	}

	public void setVeh_color(String veh_color) {
		this.veh_color = veh_color;
	}

	public String getSurveil_type() {
		return surveil_type;
	}

	public void setSurveil_type(String surveil_type) {
		this.surveil_type = surveil_type;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getReceive_time() {
		return receive_time;
	}

	public void setReceive_time(String receive_time) {
		this.receive_time = receive_time;
	}

	public String getInsert_time() {
		return insert_time;
	}

	public void setInsert_time(String insert_time) {
		this.insert_time = insert_time;
	}

	public String getPic_directory() {
		return pic_directory;
	}

	public void setPic_directory(String pic_directory) {
		this.pic_directory = pic_directory;
	}

	public String getVeh_pic() {
		return veh_pic;
	}

	public void setVeh_pic(String veh_pic) {
		this.veh_pic = veh_pic;
	}

	public String getCharacter_pic() {
		return character_pic;
	}

	public void setCharacter_pic(String character_pic) {
		this.character_pic = character_pic;
	}

	public String getStandby_pic() {
		return standby_pic;
	}

	public void setStandby_pic(String standby_pic) {
		this.standby_pic = standby_pic;
	}

	public String getVideo_path() {
		return video_path;
	}

	public void setVideo_path(String video_path) {
		this.video_path = video_path;
	}

	public String getSr_submit_time() {
		return sr_submit_time;
	}

	public void setSr_submit_time(String sr_submit_time) {
		this.sr_submit_time = sr_submit_time;
	}

	public String getSr_receive_time() {
		return sr_receive_time;
	}

	public void setSr_receive_time(String sr_receive_time) {
		this.sr_receive_time = sr_receive_time;
	}

	public String getSr_license_number() {
		return sr_license_number;
	}

	public void setSr_license_number(String sr_license_number) {
		this.sr_license_number = sr_license_number;
	}

	public String getSr_license_number_conf() {
		return sr_license_number_conf;
	}

	public void setSr_license_number_conf(String sr_license_number_conf) {
		this.sr_license_number_conf = sr_license_number_conf;
	}

	public String getSr_license_type() {
		return sr_license_type;
	}

	public void setSr_license_type(String sr_license_type) {
		this.sr_license_type = sr_license_type;
	}

	public String getSr_license_type_conf() {
		return sr_license_type_conf;
	}

	public void setSr_license_type_conf(String sr_license_type_conf) {
		this.sr_license_type_conf = sr_license_type_conf;
	}

	public String getSr_license_color() {
		return sr_license_color;
	}

	public void setSr_license_color(String sr_license_color) {
		this.sr_license_color = sr_license_color;
	}

	public String getSr_license_color_conf() {
		return sr_license_color_conf;
	}

	public void setSr_license_color_conf(String sr_license_color_conf) {
		this.sr_license_color_conf = sr_license_color_conf;
	}

	public String getSr_veh_type() {
		return sr_veh_type;
	}

	public void setSr_veh_type(String sr_veh_type) {
		this.sr_veh_type = sr_veh_type;
	}

	public String getSr_veh_type_conf() {
		return sr_veh_type_conf;
	}

	public void setSr_veh_type_conf(String sr_veh_type_conf) {
		this.sr_veh_type_conf = sr_veh_type_conf;
	}

	public String getSr_veh_color() {
		return sr_veh_color;
	}

	public void setSr_veh_color(String sr_veh_color) {
		this.sr_veh_color = sr_veh_color;
	}

	public String getSr_veh_color_conf() {
		return sr_veh_color_conf;
	}

	public void setSr_veh_color_conf(String sr_veh_color_conf) {
		this.sr_veh_color_conf = sr_veh_color_conf;
	}

	public String getSr_brand() {
		return sr_brand;
	}

	public void setSr_brand(String sr_brand) {
		this.sr_brand = sr_brand;
	}

	public String getSr_brand_conf() {
		return sr_brand_conf;
	}

	public void setSr_brand_conf(String sr_brand_conf) {
		this.sr_brand_conf = sr_brand_conf;
	}

	public String getSr_subbrand() {
		return sr_subbrand;
	}

	public void setSr_subbrand(String sr_subbrand) {
		this.sr_subbrand = sr_subbrand;
	}

	public String getSr_subbrand_conf() {
		return sr_subbrand_conf;
	}

	public void setSr_subbrand_conf(String sr_subbrand_conf) {
		this.sr_subbrand_conf = sr_subbrand_conf;
	}

	public String getSr_right_belt() {
		return sr_right_belt;
	}

	public void setSr_right_belt(String sr_right_belt) {
		this.sr_right_belt = sr_right_belt;
	}

	public String getSr_right_belt_conf() {
		return sr_right_belt_conf;
	}

	public void setSr_right_belt_conf(String sr_right_belt_conf) {
		this.sr_right_belt_conf = sr_right_belt_conf;
	}

	public String getSr_left_belt() {
		return sr_left_belt;
	}

	public void setSr_left_belt(String sr_left_belt) {
		this.sr_left_belt = sr_left_belt;
	}

	public String getSr_left_belt_conf() {
		return sr_left_belt_conf;
	}

	public void setSr_left_belt_conf(String sr_left_belt_conf) {
		this.sr_left_belt_conf = sr_left_belt_conf;
	}

	public String getSr_right_shield() {
		return sr_right_shield;
	}

	public void setSr_right_shield(String sr_right_shield) {
		this.sr_right_shield = sr_right_shield;
	}

	public String getSr_right_shield_conf() {
		return sr_right_shield_conf;
	}

	public void setSr_right_shield_conf(String sr_right_shield_conf) {
		this.sr_right_shield_conf = sr_right_shield_conf;
	}

	public String getSr_left_shield() {
		return sr_left_shield;
	}

	public void setSr_left_shield(String sr_left_shield) {
		this.sr_left_shield = sr_left_shield;
	}

	public String getSr_left_shield_conf() {
		return sr_left_shield_conf;
	}

	public void setSr_left_shield_conf(String sr_left_shield_conf) {
		this.sr_left_shield_conf = sr_left_shield_conf;
	}

	public String getSr_right_callup() {
		return sr_right_callup;
	}

	public void setSr_right_callup(String sr_right_callup) {
		this.sr_right_callup = sr_right_callup;
	}

	public String getSr_right_callup_conf() {
		return sr_right_callup_conf;
	}

	public void setSr_right_callup_conf(String sr_right_callup_conf) {
		this.sr_right_callup_conf = sr_right_callup_conf;
	}

	public String getSr_left_callup() {
		return sr_left_callup;
	}

	public void setSr_left_callup(String sr_left_callup) {
		this.sr_left_callup = sr_left_callup;
	}

	public String getSr_left_callup_conf() {
		return sr_left_callup_conf;
	}

	public void setSr_left_callup_conf(String sr_left_callup_conf) {
		this.sr_left_callup_conf = sr_left_callup_conf;
	}

	public String getSr_pendant() {
		return sr_pendant;
	}

	public void setSr_pendant(String sr_pendant) {
		this.sr_pendant = sr_pendant;
	}

	public String getSr_pendant_conf() {
		return sr_pendant_conf;
	}

	public void setSr_pendant_conf(String sr_pendant_conf) {
		this.sr_pendant_conf = sr_pendant_conf;
	}

	public String getSr_yellow_label() {
		return sr_yellow_label;
	}

	public void setSr_yellow_label(String sr_yellow_label) {
		this.sr_yellow_label = sr_yellow_label;
	}

	public String getSr_yellow_label_conf() {
		return sr_yellow_label_conf;
	}

	public void setSr_yellow_label_conf(String sr_yellow_label_conf) {
		this.sr_yellow_label_conf = sr_yellow_label_conf;
	}

	public String getSr_chemicals() {
		return sr_chemicals;
	}

	public void setSr_chemicals(String sr_chemicals) {
		this.sr_chemicals = sr_chemicals;
	}

	public String getSr_chemicals_conf() {
		return sr_chemicals_conf;
	}

	public void setSr_chemicals_conf(String sr_chemicals_conf) {
		this.sr_chemicals_conf = sr_chemicals_conf;
	}

	public String getSr_damage() {
		return sr_damage;
	}

	public void setSr_damage(String sr_damage) {
		this.sr_damage = sr_damage;
	}

	public String getSr_damage_conf() {
		return sr_damage_conf;
	}

	public void setSr_damage_conf(String sr_damage_conf) {
		this.sr_damage_conf = sr_damage_conf;
	}

	public String getBrand_birth() {
		return brand_birth;
	}

	public void setBrand_birth(String brand_birth) {
		this.brand_birth = brand_birth;
	}

	public String getExist_vehicle_license() {
		return exist_vehicle_license;
	}

	public void setExist_vehicle_license(String exist_vehicle_license) {
		this.exist_vehicle_license = exist_vehicle_license;
	}
}
