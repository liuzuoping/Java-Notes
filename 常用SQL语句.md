# 常用SQL语句

## DDL语句

### trms_data

```sql
create schema if not exists trms_data collate utf8_general_ci;

create table if not exists category
(
	id bigint auto_increment primary key,
	parent_id bigint null,
	is_parent tinyint(1) null,
	access int null,
	name varchar(100) null,
	last_update_time datetime null
);

create table if not exists category_group_access
(
	cid bigint null,
	group_id bigint null
);

create table if not exists group_user
(
	group_id bigint null,
	user_key varchar(100) null
);

create table if not exists report
(
	id bigint auto_increment primary key,
	cid bigint default 1 not null,
	uid varchar(100) not null,
	access int null,
	title varchar(100) null,
	description varchar(100) null,
	create_time datetime null,
	last_update_time datetime null,
	version varchar(100) null,
	size varchar(100) null,
	del_flag tinyint(1) default 0 not null comment '删除标识（0 否 1 是）',
	path varchar(500) null,
	cid0 bigint not null,
	version_lock int default 0 not null
);

create table if not exists report_group_access
(
	rid bigint null,
	group_id bigint null
);

create table if not exists suggestion
(
	id bigint auto_increment primary key,
	uid varchar(100) null,
	content text null,
	status int null,
	create_time datetime null
);

create table if not exists sys_group
(
	id bigint auto_increment primary key,
	name varchar(100) null,
	description varchar(100) null,
	last_update_time datetime null,
	is_exclusive tinyint(1) null
);
```

### tgzc

```sql
create schema if not exists notice_cqcs collate utf8_general_ci;

create table if not exists address_area_code_dict
(
	id bigint auto_increment comment '唯一id，自动生成'
		primary key,
	area_code varchar(32) null comment '行政区域代码',
	area_name varchar(32) null comment '行政区域名称',
	area_code_parent varchar(32) null comment '行政区域父级代码',
	comment varchar(255) null comment '备注信息',
	gmt_create datetime default CURRENT_TIMESTAMP not null comment '创建时间',
	gmt_modified timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '修改时间',
	constraint udx_area_code
		unique (area_code)
)
comment '行政区域代码字典表';

create table if not exists bridge_project_card
(
	id bigint auto_increment comment '唯一id，自动生成'
		primary key,
	object_id bigint null comment '监测对象id',
	route_number varchar(100) null comment '路线编号',
	route_level varchar(100) not null comment '路线等级',
	route_name varchar(100) null comment '路线名称',
	bridge_number varchar(100) null comment '桥梁编号',
	bridge_name varchar(100) not null comment '桥梁名称',
	bridge_position_number varchar(100) null comment '桥位桩号',
	function_type varchar(100) not null comment '功能类型',
	underpass_channel_name varchar(100) null comment '下穿通道名',
	underpass_channel_number varchar(100) null comment '下穿通道桩号',
	design_load varchar(100) null comment '设计载荷',
	traffic_load double(12,2) null comment '通行载重',
	slope_degree double(12,2) null comment '弯斜坡度',
	bridge_pavement varchar(100) null comment '桥面铺装',
	build_period varchar(100) null comment '建设年限',
	bridge_length double(12,2) null comment '桥长',
	bridge_width double(12,2) null comment '桥面总宽',
	roadway_width double(12,2) null comment '车行道宽',
	bridge_deck_elevation double(12,2) null comment '桥面标高',
	lower_clear_height double(12,2) null comment '桥下净高',
	higher_clear_height double(12,2) null comment '桥上净高',
	total_lead_width double(12,2) null comment '引道总宽',
	lead_road_width double(12,2) null comment '引道路面宽',
	lead_road_line varchar(100) null comment '引道线形',
	hole_number varchar(100) null comment '孔号',
	hole_form varchar(100) null comment '孔形式',
	hole_span varchar(100) null comment '孔跨径',
	hole_material varchar(100) null comment '孔材料',
	abutment varchar(100) null comment '墩台',
	abutment_type varchar(100) null comment '墩台形式',
	abutment_material varchar(100) null comment '墩台材料',
	abutment_base_type varchar(100) null comment '墩台基础形式',
	expansion_joint_type varchar(100) null comment '伸缩缝类型',
	support_form varchar(100) null comment '支座形式',
	acc_coefficient varchar(100) null comment '加速度系数',
	abutment_slope varchar(100) null comment '桥台护坡',
	pier_body varchar(100) null comment '护墩体',
	modulation_structure varchar(100) null comment '调制构造物',
	normal_water_level varchar(100) null comment '常水位',
	design_water_level varchar(100) null comment '设计水位',
	history_water_level varchar(100) null comment '历史洪水位',
	check_date datetime null comment '检查年月',
	periodic_inspection tinyint(1) null comment '定期或特殊检查',
	whole_bridge_grade varchar(100) null comment '全桥评定等级',
	abutment_foundation varchar(100) null comment '桥台与基础',
	pier_foundation varchar(100) null comment '桥墩与基础',
	riverbed varchar(100) null comment '河床',
	up_structure varchar(100) null comment '上部结构',
	support varchar(100) null comment '支座',
	regular_maintenance tinyint(1) null comment '经常保养小修',
	treatment varchar(100) null comment '处治对策',
	next_check_date datetime null comment '下次检查年份',
	is_deleted tinyint(1) default 0 not null comment '0-未删除；1-已删除',
	env varchar(10) default 'pro' not null comment '环境标记',
	gmt_create datetime default CURRENT_TIMESTAMP null comment '创建时间',
	gmt_modified datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '修改时间',
	remark varchar(200) null comment '备注'
)
comment '桥梁基础信息卡表';

create index idx_function_type
	on bridge_project_card (function_type, route_level);


create index idx_route_level
	on bridge_project_card (route_level);

create table if not exists building_info_card
(
	id bigint auto_increment comment '唯一id，自动生成'
		primary key,
	object_id bigint null comment '监测对象id',
	name varchar(100) not null comment '建筑名称',
	function_type varchar(100) not null comment '房屋用途',
	height double(12,2) null comment '房屋高度',
	floor_number int null comment '房屋层数',
	construction_unit varchar(100) null comment '建设单位',
	owner_info varchar(100) null comment '业主信息',
	completion_date datetime null comment '竣工日期',
	structural_style varchar(100) null comment '结构形式',
	covered_area double(12,2) null comment '建筑面积',
	test_conclusion varchar(100) null comment '检测鉴定结论',
	is_deleted tinyint(1) default 0 not null comment '0-未删除；1-已删除',
	env varchar(10) default 'pro' not null comment '环境标记',
	gmt_create datetime default CURRENT_TIMESTAMP null comment '创建时间',
	gmt_modified datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '修改时间',
	remark varchar(200) null comment '备注'
)
comment '建筑基础信息卡表';

create index idx_usage
	on building_info_card (function_type);

create table if not exists geology_disaster_card
(
	id bigint auto_increment comment '唯一id，自动生成'
		primary key,
	object_id bigint null comment '监测对象id',
	name varchar(100) not null comment '隐患点名称',
	number varchar(100) not null comment '隐患点编号',
	type varchar(100) not null comment '隐患点类型',
	length double(12,2) null comment '长',
	width double(12,2) null comment '宽',
	space double(12,2) null comment '面积',
	volume double(12,2) null comment '体积',
	grade varchar(100) not null comment '规模等级',
	threat_object varchar(100) null comment '威胁对象',
	threat_households double(12,2) null comment '威胁户数',
	threat_population double(12,2) null comment '威胁人口',
	threat_property double(12,2) null comment '威胁财产',
	induction_factor varchar(100) null comment '诱发因素',
	geological_conditions varchar(100) null comment '地质环境条件',
	deformation_feature varchar(100) null comment '变形特征及活动历史',
	stability_analysis varchar(100) null comment '稳定性分析',
	is_deleted tinyint(1) default 0 not null comment '0-未删除；1-已删除',
	env varchar(10) default 'pro' not null comment '环境标记',
	gmt_create datetime default CURRENT_TIMESTAMP null comment '创建时间',
	gmt_modified datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '修改时间',
	remark varchar(200) null comment '备注'
)
comment '地质灾害信息卡表';

create index idx_type
	on geology_disaster_card (type);

create table if not exists notice_alarm_config
(
	id bigint unsigned auto_increment comment '主键ID'
		primary key,
	name varchar(512) not null comment '预警配置名称',
	object_id bigint not null comment '监测对象id',
	points varchar(512) not null comment '监测点id集合',
	item_type varchar(16) not null comment '监测项类型，如L1_JS, L3_YL',
	send_frequency float(4,1) not null comment '告警发送频率，0.1-24,单位小时',
	config_status varchar(12) default 'enable' null comment '配置状态,enable-已启用，disable-已禁用',
	is_deleted tinyint default 0 not null comment '是否删除默认0',
	env varchar(10) default 'pro' not null comment '环境标记',
	remark varchar(255) null comment '备注',
	create_user varchar(50) default '' not null comment '创建人账号(system即为系统)',
	modify_user varchar(50) default '' not null comment '修改人账号(system即为系统)',
	gmt_create datetime default CURRENT_TIMESTAMP null comment '创建时间',
	gmt_modified datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '修改时间'
)
comment '监测对象预警配置表';

create index idx_object
	on notice_alarm_config (env);

create table if not exists notice_alarm_config_rule
(
	id bigint unsigned auto_increment comment '主键ID'
		primary key,
	config_id bigint not null comment '配置id',
	alarm_level varchar(20) null comment '预警等级',
	threshold_type varchar(12) not null comment '阈值类型, absolute-绝对值，cumulative-变化量',
	trigger_value double(6,2) not null comment '告警触发值',
	time_range int null comment '累计时间,threshold_type为cumulative不能为空',
	data_index_code varchar(20) null comment '监测值编码，如 gX，value',
	is_deleted tinyint default 0 not null comment '是否删除默认0',
	env varchar(10) default 'pro' not null comment '环境标记',
	remark varchar(255) null comment '备注',
	gmt_create datetime default CURRENT_TIMESTAMP null comment '创建时间',
	gmt_modified datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '修改时间'
)
comment '预警配置规则表';

create index idx_config
	on notice_alarm_config_rule (config_id, env);

create index idx_index
	on notice_alarm_config_rule (data_index_code);

create table if not exists notice_alarm_process_record
(
	id bigint unsigned auto_increment comment '主键ID'
		primary key,
	record_id bigint not null comment '预警记录id',
	false_positive tinyint(1) default 0 not null comment '是否误报, 0-否，1-是误报',
	process_user varchar(20) null comment '处理人账号',
	opinions varchar(512) null comment '处理意见',
	is_deleted tinyint default 0 not null comment '是否删除默认0',
	env varchar(10) default 'pro' not null comment '环境标记',
	remark varchar(255) null comment '备注',
	gmt_create datetime default CURRENT_TIMESTAMP null comment '创建时间',
	gmt_modified datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '修改时间'
)
comment '预警处理记录表';

create index idx_record
	on notice_alarm_process_record (env);

create table if not exists notice_alarm_record
(
	id bigint unsigned auto_increment comment '主键ID'
		primary key,
	code varchar(50) not null comment '预警记录编码，雪花自动生成',
	point_id bigint not null comment '监测点id',
	item_code varchar(20) not null comment '监测项编码，如L1_JS',
	trigger_time datetime default CURRENT_TIMESTAMP null comment '预警触发时间',
	alarm_level varchar(20) null comment '预警等级，red，origin，yellow，blue',
	alarm_desc varchar(512) null comment '预警描述',
	status varchar(12) not null comment '预警状态，done-已处理，todo-未处理',
	send_flag tinyint default 0 null comment '发送短信标志',
	is_deleted tinyint default 0 not null comment '是否删除默认0',
	env varchar(10) default 'pro' not null comment '环境标记',
	remark varchar(255) null comment '备注',
	gmt_create datetime default CURRENT_TIMESTAMP null comment '创建时间',
	gmt_modified datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '修改时间'
)
comment '对象预警记录表';

create index idx_point
	on notice_alarm_record (point_id, env);

create index idx_point_item_level_status
	on notice_alarm_record (point_id, item_code, alarm_level, status, env);

create table if not exists notice_alarm_send_config
(
	id bigint unsigned auto_increment comment '主键ID'
		primary key,
	object_id bigint not null comment '监测对象id',
	send_channel varchar(20) null comment '发送渠道，horn-喇叭，sms-短信',
	reciver_id bigint(64) null comment '接收人id',
	alarm_level varchar(256) null comment '预警等级编码集合，jsonstring',
	is_deleted tinyint default 0 not null comment '是否删除默认0',
	env varchar(10) default 'pro' not null comment '环境标记',
	remark varchar(255) null comment '备注',
	create_user varchar(50) default '' not null comment '创建人账号(system即为系统)',
	modify_user varchar(50) default '' not null comment '修改人账号(system即为系统)',
	gmt_create datetime default CURRENT_TIMESTAMP null comment '创建时间',
	gmt_modified datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '修改时间'
)
comment '预警发送配置表';

create index idx_object
	on notice_alarm_send_config (env);

create table if not exists notice_alarm_send_record
(
	id bigint unsigned auto_increment comment '主键ID'
		primary key,
	record_id bigint not null comment '预警记录id',
	send_channel varchar(20) null comment '发送渠道，horn-喇叭，sms-短信',
	recivers varchar(512) null comment '接收人id列表，jsonstring',
	message_id varchar(100) null comment '发送回执id',
	is_deleted tinyint default 0 not null comment '是否删除默认0',
	env varchar(10) default 'pro' not null comment '环境标记',
	remark varchar(255) null comment '备注',
	gmt_create datetime default CURRENT_TIMESTAMP null comment '创建时间',
	gmt_modified datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '修改时间'
)
comment '预警发送记录表';

create index idx_channel
	on notice_alarm_send_record (send_channel, env);

create index idx_record
	on notice_alarm_send_record (env);

create table if not exists notice_alarm_send_status
(
	id bigint unsigned auto_increment comment '主键ID'
		primary key,
	send_record_id bigint not null comment '告警发送记录id',
	send_status varchar(20) null comment '发送状态，success-成功，fail-失败',
	message_id varchar(100) null comment '发送回执id',
	reciver_id bigint null comment '接收人id',
	is_deleted tinyint default 0 not null comment '是否删除默认0',
	env varchar(10) default 'pro' not null comment '环境标记',
	remark varchar(255) null comment '备注',
	gmt_create datetime default CURRENT_TIMESTAMP null comment '创建时间',
	gmt_modified datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '修改时间'
)
comment '告警发送状态表';

create index idx_message
	on notice_alarm_send_status (message_id, env);

create index idx_record
	on notice_alarm_send_status (env);

create index idx_status
	on notice_alarm_send_status (send_status, env);

create table if not exists notice_alarm_template
(
	id bigint auto_increment comment '唯一id，自动生成'
		primary key,
	object_type varchar(30) not null comment '监测对象类型编码，例：ql',
	object_type_name varchar(100) null comment '监测对象类型名称，例：ql',
	creator varchar(30) not null comment '创建人',
	creator_name varchar(30) not null comment '创建人姓名',
	modifier varchar(30) null comment '更新人',
	modifier_name varchar(30) null comment '更新人姓名',
	is_deleted tinyint(1) default 0 not null comment '0-未删除；1-已删除',
	env varchar(10) default 'pro' not null comment '环境标记',
	gmt_create datetime default CURRENT_TIMESTAMP null comment '创建时间',
	gmt_modified datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '修改时间',
	remark varchar(200) null comment '备注'
)
comment '预警模板表';

create index idx_object_type
	on notice_alarm_template (object_type);

create table if not exists notice_alarm_template_config
(
	id bigint auto_increment comment '唯一id，自动生成'
		primary key,
	template_id bigint not null comment '模板id',
	object_type varchar(50) not null comment '监测对象类型编码，例：ql',
	object_type_name varchar(100) null comment '监测对象类型名称，例：ql',
	item_code varchar(50) not null comment '监测项code，例 L1_JS',
	item_name varchar(100) null comment '监测项名称，例 L1_JS',
	index_code varchar(50) not null comment '监测项字段编码, 例：gX',
	index_name varchar(50) null comment '监测项字段名称, 例：gX',
	index_unit varchar(50) null comment '监测项单位, 例：mm',
	index_unit_name varchar(30) null comment '监测项单位名称, 例：毫米',
	is_enable tinyint(1) default 0 null comment '是否启用0-关闭；1-启用',
	level varchar(50) null comment '告警级别，例：red',
	trigger_value float(10,2) null comment '触发值，如20',
	time_range float(10,2) null comment '累计时间，如 2',
	threshold_type varchar(30) null comment '阈值类型, absolute-绝对值，cumulative-变化量',
	is_deleted tinyint(1) default 0 not null comment '0-未删除；1-已删除',
	env varchar(10) default 'pro' not null comment '环境标记',
	gmt_create datetime default CURRENT_TIMESTAMP null comment '创建时间',
	gmt_modified datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '修改时间',
	remark varchar(200) null comment '备注'
)
comment '预警模板配置表';

create index idx_template_id
	on notice_alarm_template_config (template_id);

create table if not exists notice_index_config
(
	id bigint auto_increment comment '唯一id，自动生成'
		primary key,
	sensor_type_code varchar(30) not null comment '传感器类别id YL：雨量、gnss:GNSS、JS:加速度、QJ:倾角、LF:裂缝、HS:含水率',
	sensor_index varchar(50) not null comment '传感器类别指标，例如JS加速度：x-X向加速度及瞬间冲击加速度、y-Y向加速度及瞬间冲击加速度、z-Z向加速度及瞬间冲击加速度',
	sensor_index_name varchar(100) not null comment '传感器类别指标，例如JS加速度：x-X向加速度及瞬间冲击加速度、y-Y向加速度及瞬间冲击加速度、z-Z向加速度及瞬间冲击加速度',
	metric varchar(100) not null comment 'metric',
	metric_source varchar(20) not null comment '字段来源：device-仪器，platform-平台',
	metric_formula varchar(500) null comment 'platform tag 公式',
	metric_name varchar(100) not null comment 'metric名称',
	unit varchar(50) null comment '单位',
	unit_name varchar(100) null comment '单位名称',
	is_deleted tinyint(1) default 0 not null comment '0-未删除；1-已删除',
	gmt_create datetime default CURRENT_TIMESTAMP null comment '创建时间',
	gmt_modified datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '修改时间',
	remark varchar(200) null comment '备注',
	constraint uni_metric
		unique (sensor_type_code, sensor_index, metric)
)
comment '配置表';

create index idx_index
	on notice_index_config (sensor_type_code);

create table if not exists notice_inspection
(
	inspection_id bigint auto_increment
		primary key,
	inspection_time datetime default CURRENT_TIMESTAMP null comment '巡检时间',
	object_id bigint null comment '监测对象id',
	inspector varchar(32) null comment '巡检人名字',
	commit_status varchar(32) null comment 'uncommit和commit',
	is_deleted tinyint(1) default 0 not null comment '0-未删除；1-已删除',
	create_user varchar(50) default '' not null comment '创建人账号(system即为系统)',
	modify_user varchar(50) default '' not null comment '修改人账号(system即为系统)',
	gmt_create datetime default CURRENT_TIMESTAMP not null comment '创建时间',
	gmt_modified datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '修改时间'
)
comment '巡检记录表' charset=utf8mb4;

create index notice_inspection_inspection_time_index
	on notice_inspection (inspection_time);

create index notice_inspection_object_id_index
	on notice_inspection (object_id);

create table if not exists notice_inspection_detail
(
	inspection_detail_id bigint auto_increment comment '巡检详情id （主键）'
		primary key,
	inspection_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
	object_id bigint null comment '检测项id',
	inspector varchar(32) null comment '巡检人',
	inspection_site varchar(255) not null comment '巡检部位',
	inspection_result varchar(64) not null comment '巡检结果，healthy-完好，unhealthy-不良',
	resource_list text null comment 'url 链接（图片，视频等）json',
	suggest varchar(255) null comment '巡检建议',
	inspection_id bigint null comment '巡检记录id（父id）',
	is_deleted tinyint(1) default 0 not null comment '0-未删除；1-已删除',
	create_user varchar(50) default '' not null comment '创建人账号(system即为系统)',
	modify_user varchar(50) default '' not null comment '修改人账号(system即为系统)',
	gmt_create datetime default CURRENT_TIMESTAMP not null comment '创建时间',
	gmt_modified datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '修改时间',
	commit_status varchar(32) default 'uncommit' null
)
comment '巡检记录详情表' charset=utf8mb4;

create index notice_inspection_detail_inspection_id_index
	on notice_inspection_detail (inspection_id);

create index notice_inspection_detail_inspection_time_index
	on notice_inspection_detail (inspection_time);

create table if not exists notice_monitor_item
(
	id bigint unsigned auto_increment comment '自增ID'
		primary key,
	point_id bigint unsigned not null comment '监测点id',
	device_id bigint unsigned null comment '关联设备id',
	name varchar(64) null comment '名称',
	type varchar(12) null comment '类型，LF-裂缝，JS-加速',
	is_deleted tinyint default 0 not null comment '是否删除默认0',
	env varchar(10) default 'pro' not null comment '环境标记',
	remark varchar(255) null comment '备注',
	gmt_create datetime default CURRENT_TIMESTAMP not null comment '创建时间',
	gmt_modified datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '修改时间',
	sensor_id varchar(64) null comment '设备编码'
)
comment '监测项表';

create index idx_device
	on notice_monitor_item (device_id, env);

create index idx_point
	on notice_monitor_item (env);

create table if not exists notice_monitor_object
(
	id bigint unsigned auto_increment comment '自增ID'
		primary key,
	code varchar(50) not null comment '监测对象编码',
	name varchar(64) not null comment '监测对象名称',
	alias_name varchar(64) null comment '监测对象别名',
	type varchar(32) not null comment '监测对象类型, dzzh-地质灾害,csql-城市桥梁、slkb-水利库坝、wjfw-危旧房屋、other-其他',
	area_code varchar(20) not null comment '行政区划code',
	province_code varchar(20) null comment '省行政区划code',
	city_code varchar(20) null comment '市行政区划code',
	area_detail varchar(200) not null comment '地理位置',
	lalType varchar(20) not null comment '经纬度类型',
	longitude varchar(20) not null comment '经度',
	latitude varchar(20) not null comment '纬度',
	altitude varchar(20) null comment '高程海拔，单位米',
	monitor_unit_name varchar(128) null comment '监测单位名称',
	monitor_unit_linkman varchar(32) null comment '监测单位联系人',
	monitor_unit_mobile varchar(16) null comment '监测单位电话',
	construction_unit_name varchar(128) null comment '施工单位名称',
	construction_unit_linkman varchar(32) null comment '施工单位联系人',
	construction_unit_mobile varchar(16) null comment '施工单位电话',
	op_unit_name varchar(128) null comment '监测单位名称',
	op_unit_linkman varchar(32) null comment '监测单位联系人',
	op_unit_mobile varchar(16) null comment '监测单位电话',
	object_desc varchar(255) null comment '监测对象描述',
	status varchar(32) null comment '监测对象状态,预留字段',
	is_deleted tinyint default 0 not null comment '是否删除默认0',
	env varchar(10) default 'pro' not null comment '环境标记',
	remark varchar(255) null comment '备注',
	create_user varchar(50) default '' not null comment '创建人账号(system即为系统)',
	modify_user varchar(50) default '' not null comment '修改人账号(system即为系统)',
	gmt_create datetime default CURRENT_TIMESTAMP not null comment '创建时间',
	gmt_modified datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '修改时间'
)
comment '监测对象属性表';

create index idx_area_type
	on notice_monitor_object (area_code, type, env);

create index idx_name_area_type
	on notice_monitor_object (name, area_code, type, env);

create index idx_type
	on notice_monitor_object (type, env);

create table if not exists notice_monitor_object_member_ref
(
	id bigint unsigned auto_increment comment '主键ID'
		primary key,
	object_id bigint null comment '监测对象id',
	sub_user_id bigint null comment '用户id',
	is_deleted tinyint default 0 not null comment '是否删除默认0.',
	env varchar(10) default 'pro' not null comment '环境标记',
	remark varchar(255) null comment '备注',
	gmt_create datetime default CURRENT_TIMESTAMP null comment '创建时间',
	gmt_modified datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '修改时间'
)
comment '监测对象用户关联表';

create index idx_object
	on notice_monitor_object_member_ref (env);

create index idx_user
	on notice_monitor_object_member_ref (sub_user_id, env);

create table if not exists notice_monitor_object_point_ref
(
	id bigint unsigned auto_increment comment '主键ID'
		primary key,
	monitor_id bigint null comment '监测对象id',
	point_code varchar(50) null comment '监测点code',
	is_deleted tinyint default 0 not null comment '是否删除默认0.',
	env varchar(10) default 'pro' null comment '环境',
	remark varchar(255) null comment '备注',
	gmt_create datetime default CURRENT_TIMESTAMP null comment '创建时间',
	gmt_modified datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '修改时间'
)
comment '监测对象与监测点关系表';

create index idx_monitor_id
	on notice_monitor_object_point_ref (env);

create index idx_point_code
	on notice_monitor_object_point_ref (point_code, env);

create table if not exists notice_monitor_object_resource_ref
(
	id bigint unsigned auto_increment comment '主键ID'
		primary key,
	object_id bigint null comment '监测对象id',
	resource_id varchar(64) null comment '资源id',
	resource_url varchar(256) null comment '资源存储url，oss方式',
	resource_name varchar(128) null comment '资源名称',
	resource_type varchar(16) null comment '资源类型,design-设计，construction-施工，accepting-验收，gain-成果',
	resource_save_type varchar(16) null comment '资源保存方式，oss-oss，local-本地',
	is_deleted tinyint default 0 not null comment '是否删除默认0.',
	env varchar(10) default 'pro' not null comment '环境标记',
	remark varchar(255) null comment '备注',
	gmt_create datetime default CURRENT_TIMESTAMP null comment '创建时间',
	gmt_modified datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '修改时间'
)
comment '监测对象资源表';

create index idx_object
	on notice_monitor_object_resource_ref (env);

create index idx_resource
	on notice_monitor_object_resource_ref (resource_id, env);

create table if not exists notice_monitor_object_template_ref
(
	id bigint auto_increment comment '唯一id，自动生成'
		primary key,
	name varchar(255) not null comment '文件名称',
	monitor_id bigint null comment '监测对象id',
	url varchar(256) null comment '资源存储url，oss方式',
	type varchar(30) not null comment '类型，month-月，week-周，artificial-人工',
	is_deleted tinyint(1) default 0 not null comment '0-未删除；1-已删除',
	env varchar(10) default 'pro' not null comment '环境标记',
	upload_time datetime default CURRENT_TIMESTAMP null comment '上传时间',
	gmt_create datetime default CURRENT_TIMESTAMP null comment '创建时间',
	gmt_modified datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '修改时间',
	remark varchar(200) null comment '备注'
)
comment '监测对象与模板关系表';

create index idx_monitor_id
	on notice_monitor_object_template_ref (env);

create table if not exists notice_monitor_point
(
	id bigint unsigned auto_increment comment '自增ID'
		primary key,
	code varchar(64) not null comment '编码',
	name varchar(64) not null comment '名称',
	longitude varchar(20) not null comment '经度,十进制',
	latitude varchar(20) not null comment '纬度,十进制',
	altitude varchar(20) null comment '高程海拔，单位米',
	point_desc varchar(255) null comment '监测点描述',
	status varchar(32) null comment '监测点状态,预留字段',
	is_deleted tinyint default 0 not null comment '是否删除默认0',
	env varchar(10) default 'pro' not null comment '环境标记',
	remark varchar(255) null comment '备注',
	gmt_create datetime default CURRENT_TIMESTAMP not null comment '创建时间',
	gmt_modified datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '修改时间'
)
comment '监测点属性表';

create index idx_name
	on notice_monitor_point (env);

create table if not exists notice_monitor_point_device_ref
(
	id bigint unsigned auto_increment comment '主键ID'
		primary key,
	point_id bigint null comment '项目id',
	device_id varchar(50) null comment '设备id',
	is_deleted tinyint default 0 not null comment '是否删除默认0.',
	device_status varchar(12) null comment '状态,online-在线，offline-离线',
	bind_status varchar(12) null comment '绑定状态,valid-有效，invalid-无效',
	env varchar(10) default 'pro' null comment '环境',
	remark varchar(255) null comment '备注',
	gmt_create datetime default CURRENT_TIMESTAMP null comment '创建时间',
	gmt_modified datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '修改时间'
)
comment '监测点与设备关系表';

create index idx_device_id
	on notice_monitor_point_device_ref (device_id, env);

create table if not exists notice_object_lifecycle_milestone
(
	id bigint unsigned auto_increment comment '自增ID'
		primary key,
	object_id bigint not null comment '监测对象id',
	milestone_name varchar(100) not null comment '事件名称',
	milestone_type varchar(50) not null comment '事件类型',
	milestone_desc varchar(200) null comment '事件描述',
	milestone_time datetime null comment '创建时间',
	is_deleted tinyint default 0 not null comment '是否删除默认0',
	env varchar(10) default 'pro' not null comment '环境标记',
	remark varchar(255) null comment '备注',
	gmt_create datetime default CURRENT_TIMESTAMP not null comment '创建时间',
	gmt_modified datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '修改时间'
)
comment '监测对象生命周期事件表';

create table if not exists notice_operate_log
(
	id bigint unsigned auto_increment comment '主键ID'
		primary key,
	op_type varchar(64) not null comment '操作类型， delete-删除, create-添加, edit-编辑',
	op_user varchar(128) not null comment '操作人域账号',
	user_id bigint null comment '账号id',
	op_param varchar(1024) not null comment '接口入参 jsonString格式',
	is_deleted tinyint default 0 not null comment '是否删除默认0',
	env varchar(10) default 'pro' not null comment '环境标记',
	remark varchar(255) null comment '备注',
	gmt_create datetime default CURRENT_TIMESTAMP null comment '创建时间',
	gmt_modified datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '修改时间'
)
comment '操作日志表';

create index idx_op_type
	on notice_operate_log (op_type, env);

create table if not exists notice_project
(
	id bigint unsigned auto_increment comment '自增ID'
		primary key,
	code varchar(50) not null comment '项目code',
	name varchar(64) not null comment '项目名称',
	area_code varchar(20) null comment '行政区划code',
	province_code varchar(20) null comment '省行政区划code',
	city_code varchar(20) null comment '市行政区划code',
	start_year varchar(20) null comment '项目年度',
	start_date datetime null comment '启动日期',
	end_date datetime null comment '验收日期',
	contractor varchar(128) null comment '承建单位',
	contact_person varchar(64) null comment '业主联系人',
	mobile varchar(64) null comment '联系电话',
	is_deleted tinyint default 0 not null comment '是否删除默认0',
	env varchar(10) default 'pro' not null comment '环境标记',
	remark varchar(255) null comment '备注',
	create_user varchar(50) default '' not null comment '创建人账号(system即为系统)',
	modify_user varchar(50) default '' not null comment '修改人账号(system即为系统)',
	gmt_create datetime default CURRENT_TIMESTAMP not null comment '创建时间',
	gmt_modified datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '修改时间',
	constraint uniq_code
		unique (code, env)
)
comment '项目属性表';

create index idx_area_code
	on notice_project (area_code, env);

create index idx_area_code_year_name
	on notice_project (name, area_code, start_year, env);

create index idx_year
	on notice_project (start_year, env);

create table if not exists notice_project_monitor_ref
(
	id bigint unsigned auto_increment comment '主键ID'
		primary key,
	project_id bigint null comment '项目id',
	monitor_code varchar(50) null comment '监测对象code',
	is_deleted tinyint default 0 not null comment '是否删除默认0.',
	env varchar(10) default 'pro' null comment '环境',
	remark varchar(255) null comment '备注',
	gmt_create datetime default CURRENT_TIMESTAMP null comment '创建时间',
	gmt_modified datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '修改时间'
)
comment '项目与监测对象关系表';

create index idx_monitor_code
	on notice_project_monitor_ref (monitor_code, env);

create index idx_project_id
	on notice_project_monitor_ref (project_id, env);

create table if not exists notice_project_owner
(
	id bigint unsigned auto_increment comment '主键ID'
		primary key,
	user_id bigint not null comment '用户id',
	user_mobile varchar(100) null comment '用户手机号',
	user_name varchar(100) null comment '用户名',
	effect_date datetime null comment '开通时间',
	expired_date datetime null comment '过期时间',
	status varchar(64) null comment '主体状态，active-已开通、cancel-已取消',
	is_deleted tinyint default 0 not null comment '是否删除默认0.',
	env varchar(10) default 'pro' null comment '环境',
	remark varchar(255) null comment '备注',
	gmt_create datetime default CURRENT_TIMESTAMP null comment '创建时间',
	gmt_modified datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '修改时间'
)
comment '项目主体表';

create index idx_user_id
	on notice_project_owner (env);

create table if not exists notice_receiver
(
	id bigint unsigned auto_increment comment '自增ID'
		primary key,
	project_owner_id bigint unsigned not null comment '项目主体id',
	receiver_name varchar(64) not null comment '接收人姓名',
	receiver_mobile varchar(100) null comment '用户手机号',
	type varchar(32) not null comment '接收人类型',
	is_deleted tinyint default 0 not null comment '是否删除默认0',
	env varchar(10) default 'pro' not null comment '环境标记',
	remark varchar(255) null comment '备注',
	create_user varchar(50) default '' not null comment '创建人账号(system即为系统)',
	modify_user varchar(50) default '' not null comment '修改人账号(system即为系统)',
	gmt_create datetime default CURRENT_TIMESTAMP not null comment '创建时间',
	gmt_modified datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '修改时间'
)
comment '预警接收人表';

create index idx_name
	on notice_receiver (receiver_name);

create index type
	on notice_receiver (type);

create table if not exists notice_theme_borehole
(
	id bigint auto_increment comment '唯一id，自动生成'
		primary key,
	theme_id bigint not null comment '专题分析id',
	borehole_type varchar(30) not null comment '钻孔类型：地下水位、深部位移',
	borehole_name varchar(100) not null comment '钻孔名称',
	borehole_part varchar(100) null comment '钻孔部位',
	borehole_orientation varchar(10) null comment '钻孔朝向：迎水面、背水面',
	borehole_altitude double(8,2) not null comment '钻孔海拔',
	borehole_depth double(6,2) not null comment '钻孔深度',
	borehole_diameter double(6,2) null comment '钻孔直径',
	is_deleted tinyint(1) default 0 not null comment '0-未删除；1-已删除',
	env varchar(10) default 'pro' not null comment '环境标记',
	gmt_create datetime default CURRENT_TIMESTAMP null comment '创建时间',
	gmt_modified datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '修改时间',
	remark varchar(200) null comment '备注'
)
comment '主题分析钻孔表';

create table if not exists notice_theme_borehole_point
(
	id bigint auto_increment comment '唯一id，自动生成'
		primary key,
	borehole_id bigint not null comment '钻孔id',
	point_id bigint not null comment '监测点id',
	install_depth double(8,2) not null comment '安装深度',
	is_deleted tinyint(1) default 0 not null comment '0-未删除；1-已删除',
	env varchar(10) default 'pro' not null comment '环境标记',
	gmt_create datetime default CURRENT_TIMESTAMP null comment '创建时间',
	gmt_modified datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '修改时间',
	remark varchar(200) null comment '备注'
)
comment '钻孔关联监测点表';

create index idx_index
	on notice_theme_borehole_point (install_depth);

create table if not exists notice_theme_object
(
	id bigint auto_increment comment '唯一id，自动生成'
		primary key,
	object_id bigint not null comment '监测对象id',
	theme_type_code varchar(50) not null comment '专题分析类型：地下水位、库水位、渗压断面、视频监测、渗流量、沉降测线、深部位移、雨量',
	theme_type_name varchar(100) not null comment '专题分析类型：地下水位、库水位、渗压断面、视频监测、渗流量、沉降测线、深部位移、雨量',
	is_show tinyint(1) default 1 not null comment '0-不展示；1-可以展示',
	is_deleted tinyint(1) default 0 not null comment '0-未删除；1-已删除',
	env varchar(10) default 'pro' not null comment '环境标记',
	gmt_create datetime default CURRENT_TIMESTAMP null comment '创建时间',
	gmt_modified datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '修改时间',
	remark varchar(200) null comment '备注'
)
comment '监测对象专题分析主表';

create index idx_object_id
	on notice_theme_object (object_id);

create table if not exists notice_theme_seepage_flow
(
	id bigint auto_increment comment '唯一id，自动生成'
		primary key,
	theme_id bigint not null comment '专题分析id',
	seepage_flow_name varchar(100) not null comment '渗流量名称',
	water_level_id bigint not null comment '关联库水位id',
	rainfall_point_id bigint not null comment '关联雨量监测点id',
	seepage_flow_point_id bigint not null comment '关联渗流量监测点id',
	is_deleted tinyint(1) default 0 not null comment '0-未删除；1-已删除',
	env varchar(10) default 'pro' not null comment '环境标记',
	gmt_create datetime default CURRENT_TIMESTAMP null comment '创建时间',
	gmt_modified datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '修改时间',
	remark varchar(200) null comment '备注',
	constraint uni_theme_borehole
		unique (theme_id, seepage_flow_name, is_deleted)
)
comment '主题分析渗流量表';

create index idx_theme_id
	on notice_theme_seepage_flow (theme_id);

create table if not exists notice_theme_seepage_section
(
	id bigint auto_increment comment '唯一id，自动生成'
		primary key,
	theme_id bigint not null comment '专题分析id',
	section_name varchar(100) not null comment '渗压断面名称',
	install_part varchar(100) null comment '安装部位',
	water_level_id bigint not null comment '关联库水位id',
	borehole_ids varchar(100) not null comment '关联钻孔ids,逗号分割',
	is_deleted tinyint(1) default 0 not null comment '0-未删除；1-已删除',
	env varchar(10) default 'pro' not null comment '环境标记',
	gmt_create datetime default CURRENT_TIMESTAMP null comment '创建时间',
	gmt_modified datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '修改时间',
	remark varchar(200) null comment '备注'
)
comment '主题分析渗压断面表';

create index idx_theme_id
	on notice_theme_seepage_section (theme_id);

create table if not exists notice_theme_survey_line
(
	id bigint auto_increment comment '唯一id，自动生成'
		primary key,
	theme_id bigint not null comment '专题分析id',
	survey_line_name varchar(100) not null comment '测线名称',
	survey_line_part varchar(100) null comment '测线部位',
	is_deleted tinyint(1) default 0 not null comment '0-未删除；1-已删除',
	env varchar(10) default 'pro' not null comment '环境标记',
	gmt_create datetime default CURRENT_TIMESTAMP null comment '创建时间',
	gmt_modified datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '修改时间',
	remark varchar(200) null comment '备注'
)
comment '主题分析沉降挠度测线表';

create index idx_theme_id
	on notice_theme_survey_line (theme_id);

create table if not exists notice_theme_survey_line_point
(
	id bigint auto_increment comment '唯一id，自动生成'
		primary key,
	survey_line_id bigint not null comment '测线id',
	point_id bigint not null comment '监测点id',
	point_order tinyint not null comment '监测点排序',
	is_deleted tinyint(1) default 0 not null comment '0-未删除；1-已删除',
	env varchar(10) default 'pro' not null comment '环境标记',
	gmt_create datetime default CURRENT_TIMESTAMP null comment '创建时间',
	gmt_modified datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '修改时间',
	remark varchar(200) null comment '备注'
)
comment '测线关联监测点表';

create index idx_line_point
	on notice_theme_survey_line_point (survey_line_id, point_id, point_order);

create table if not exists notice_theme_water_level
(
	id bigint auto_increment comment '唯一id，自动生成'
		primary key,
	theme_id bigint not null comment '专题分析id',
	water_level_name varchar(100) not null comment '库水位名称',
	install_part varchar(100) null comment '安装部位',
	observation_method varchar(100) not null comment '观测方式：压力式水位计、雷达水位计',
	reference_value double(8,2) not null comment '基准测值',
	reference_water_level double(8,2) not null comment '基准水位',
	device_altitude double(8,2) not null comment '设备海拔',
	point_id bigint not null comment '关联监测点id',
	is_deleted tinyint(1) default 0 not null comment '0-未删除；1-已删除',
	env varchar(10) default 'pro' not null comment '环境标记',
	gmt_create datetime default CURRENT_TIMESTAMP null comment '创建时间',
	gmt_modified datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '修改时间',
	remark varchar(200) null comment '备注'
)
comment '主题分析库水位表';

create index idx_theme_id
	on notice_theme_water_level (theme_id);


create table if not exists water_project_card
(
	id bigint auto_increment comment '唯一id，自动生成'
		primary key,
	object_id bigint null comment '监测对象id',
	name varchar(100) not null comment '水库名称',
	domain varchar(100) null comment '隶属流域',
	dam_structure varchar(100) not null comment '坝体构造',
	dam_grade varchar(100) not null comment '大坝级别',
	reservoir_grade varchar(100) not null comment '水库级别',
	running_stage varchar(100) null comment '运行阶段',
	dam_height double(12,2) not null comment '坝顶高程',
	dam_foundation_elevation double(12,2) null comment '坝基高程',
	dam_length double(12,2) null comment '坝顶长度',
	dam_width double(12,2) null comment '坝顶宽度',
	upstream_dam_slope double(12,2) null comment '上游坝坡坡度',
	downstream_dam_slope double(12,2) null comment '下游坝坡坡度',
	downstream_dam_length double(12,2) null comment '下游坝坡最大坡长',
	max_height_difference double(12,2) null comment '最大高差',
	stone tinyint(1) null comment '坝脚块石排水体',
	normal_water_height double(12,2) not null comment '正常蓄水位高程',
	dead_water_height double(12,2) not null comment '死水位高程',
	design_flood_height double(12,2) null comment '设计洪水位高程',
	check_flood_height double(12,2) null comment '校核洪水位高程',
	flood_control_height double(12,2) null comment '防洪限制水位高程',
	flood_high_height double(12,2) null comment '防洪高水位高程',
	flood_control_level varchar(100) null comment '抗洪安全性级别',
	deformation_security_level varchar(100) null comment '变形安全性级别',
	seepage_security_level varchar(100) null comment '渗流安全性级别',
	structure_security_level varchar(100) null comment '结构安全性级别',
	dam_security_code varchar(100) null comment '大坝安全综合评价类别',
	is_deleted tinyint(1) default 0 not null comment '0-未删除；1-已删除',
	env varchar(10) default 'pro' not null comment '环境标记',
	gmt_create datetime default CURRENT_TIMESTAMP null comment '创建时间',
	gmt_modified datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '修改时间',
	remark varchar(200) null comment '备注'
)
comment '水利工程信息卡表';

```





## DML语句

##### 一、DDL

```sql
CREATE TABLE `ops_enterprise` (  
    `id` BIGINT ( 20 ) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增主键',
    `enterprise_no` varchar ( 50 ) NULL DEFAULT NULL COMMENT '企业编号',
    `enterprise_name` varchar (100) NULL DEFAULT NULL COMMENT '企业名称',
    `enterprise_telephone` varchar (50) NULL DEFAULT NULL COMMENT '企业联系电话',
    `enterprise_address`  varchar (300)  NULL DEFAULT NULL COMMENT '企业联系地址',
    `legal_person`  varchar (20)  NULL DEFAULT NULL COMMENT '企业法人',
    `social_credit_code`  varchar (50)  NULL DEFAULT NULL COMMENT '统一社会信用代码',
    `registered_capital`  varchar (50)  NULL DEFAULT NULL COMMENT '注册资本',
    `registered_time`  datetime  NULL DEFAULT NULL COMMENT '注册时间',
    `enterprise_type`  varchar (50)  NULL DEFAULT NULL COMMENT '企业类型,limited-有限责任公司，other-其他有限责任公司',
    `enterprise_status`  varchar (10)  NULL DEFAULT NULL COMMENT '企业状态, enable-启用，disable-禁用',
    `pay_callback_url`  varchar (500)  NULL DEFAULT NULL COMMENT '支付回调地址',
    `data_sync_url`  varchar (500)  NULL DEFAULT NULL COMMENT '数据同步地址',
    `rsa_public_key`  varchar (2000)  NULL DEFAULT NULL COMMENT '加密公钥',
  	`company_public_key` varchar(2000) null default null comment '调用企业接口加密公钥',
    `company_private_key` varchar(2000) null default null comment '调用企业接口加密私钥',
    `sync_status`  varchar (10)  NULL DEFAULT 0 COMMENT '同步状态，0-未同步 1-已同步',
    `env` VARCHAR ( 10 ) NULL DEFAULT NULL COMMENT '环境标',
    `remark` VARCHAR ( 255 ) NULL DEFAULT NULL COMMENT '说明',
    `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted` BIGINT ( 20 ) NOT NULL DEFAULT 0 COMMENT '删除状态 0-未删除 1-已删除',
    PRIMARY KEY ( `id` ),
   	UNIQUE KEY `UNI_ENTERPRISE` (`enterprise_no`,`enterprise_status`),
    INDEX `IDX_ENTERPRISE_NO` ( `enterprise_no` )
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='企业信息';

CREATE TABLE `ops_enterprise_employee` (  
    `id` BIGINT ( 20 ) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增主键',
    `enterprise_no` varchar ( 50 ) NULL DEFAULT NULL COMMENT '企业编号',
    `enterprise_name` varchar (100) NULL DEFAULT NULL COMMENT '企业名称',
    `employee_name` varchar (50) NULL DEFAULT NULL COMMENT '员工姓名',
    `area`  varchar (100)  NULL DEFAULT NULL COMMENT '管理区域',
    `position`  varchar (100)  NULL DEFAULT NULL COMMENT '职位',
    `telephone`  varchar (20)  NULL DEFAULT NULL COMMENT '联系电话',
    `duty`  varchar (100)  NULL DEFAULT NULL COMMENT '职责',
    `entry_time`  datetime  NULL DEFAULT NULL COMMENT '入职时间',
    `sex`  varchar (10)  NULL DEFAULT NULL COMMENT '性别，male-男 female-女',
    `status`  varchar (10)  NULL DEFAULT NULL COMMENT '在职状态',
    `deparment`  varchar (10)  NULL DEFAULT NULL COMMENT '所属部门',
    `sync_status`  varchar (10)  NULL DEFAULT 0 COMMENT '同步状态，0-未同步 1-已同步',
    `env` VARCHAR ( 10 ) NULL DEFAULT NULL COMMENT '环境标',
    `remark` VARCHAR ( 255 ) NULL DEFAULT NULL COMMENT '说明',
    `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted` BIGINT ( 20 ) NOT NULL DEFAULT 0 COMMENT '删除状态 0-未删除 1-已删除',
    PRIMARY KEY ( `id` ),
    INDEX `IDX_ENTERPRISE_NO` ( `enterprise_no` ),
    INDEX `IDX_EMPLOYEE_NAME` ( `employee_name` )
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='企业员工信息';

CREATE TABLE `ops_user` (  
    `id` BIGINT ( 20 ) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增主键',
    `name` varchar (50) NULL DEFAULT NULL COMMENT '姓名',
    `id_no` varchar (50)  NULL DEFAULT NULL COMMENT '身份证号码',
    `sex`  varchar (50)  NULL DEFAULT NULL COMMENT '身份证号码', 
    `sync_status`  varchar (10)  NULL DEFAULT 0 COMMENT '同步状态，0-未同步 1-已同步',
    `env` VARCHAR ( 10 ) NULL DEFAULT NULL COMMENT '环境标',
    `remark` VARCHAR ( 255 ) NULL DEFAULT NULL COMMENT '说明',
    `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted` BIGINT ( 20 ) NOT NULL DEFAULT 0 COMMENT '删除状态 0-未删除 1-已删除',
    PRIMARY KEY ( `id` ),
    UNIQUE KEY `UNI_NAME_ID` (`name`,`id_no`),
    INDEX `IDX_NAME` ( `name` ),
    INDEX `IDX_ID_NO` ( `id_no` )
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户信息';

CREATE TABLE `ops_enterprise_user` (  
    `id` BIGINT ( 20 ) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增主键',
    `enterprise_no` varchar ( 50 ) NULL DEFAULT NULL COMMENT '企业编号',
    `enterprise_name` varchar (100) NULL DEFAULT NULL COMMENT '企业名称',
    `master_id` BIGINT ( 20 ) NULL DEFAULT NULL COMMENT '主账号ID',
    `enterprise_user_id` varchar (100) NULL DEFAULT NULL COMMENT '企业用户ID',
    `nick_name` varchar (100) NULL DEFAULT NULL COMMENT '昵称',
    `telephone`  varchar (20)  NULL DEFAULT NULL COMMENT '联系电话',
    `registered_city`  varchar (50)  NULL DEFAULT NULL COMMENT '注册城市',
    `registered_channel`  varchar (100)  NULL DEFAULT NULL COMMENT '注册渠道',
    `registered_time`  datetime  NULL DEFAULT NULL COMMENT '注册时间',
    `status`  varchar (10)  NULL DEFAULT NULL COMMENT '用户状态 normal-正常 cancel-注销',
    `sync_status`  varchar (10)  NULL DEFAULT 0 COMMENT '同步状态，0-未同步 1-已同步',
    `env` VARCHAR ( 10 ) NULL DEFAULT NULL COMMENT '环境标',
    `remark` VARCHAR ( 255 ) NULL DEFAULT NULL COMMENT '说明',
    `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted` BIGINT ( 20 ) NOT NULL DEFAULT 0 COMMENT '删除状态 0-未删除 1-已删除',
    PRIMARY KEY ( `id` ),
    UNIQUE KEY `UNI_USER` (`enterprise_no`,`enterprise_user_id`,`status`),
    INDEX `IDX_MASTER_ID` ( `master_id` ),
    INDEX `IDX_ENTERPRISE_USER_ID` ( `enterprise_user_id` ),
    INDEX `IDX_ENTERPRISE_NO` ( `enterprise_no` ),
    INDEX `IDX_TELEPHONE` ( `telephone` )
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='企业用户信息';

CREATE TABLE `ops_enterprise_bicycle` (  
    `id` BIGINT ( 20 ) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增主键',
    `enterprise_no` varchar ( 50 ) NULL DEFAULT NULL COMMENT '企业编号',
    `enterprise_name` varchar (100) NULL DEFAULT NULL COMMENT '企业名称',
    `control_manufacturer` varchar (200) NULL DEFAULT NULL COMMENT '中控厂商',
    `control_id` varchar (100) NULL DEFAULT NULL COMMENT '中控ID',
    `bicycle_number` varchar (100) NULL DEFAULT NULL COMMENT '车牌号',
    `qr_code`  varchar (200)  NULL DEFAULT NULL COMMENT '二维码编号',
    `qx_account` varchar (100)  NULL DEFAULT NULL COMMENT '千寻定位账号',
    `produce_time`  datetime  NULL DEFAULT NULL COMMENT '生产时间',
    `launch_time`  datetime  NULL DEFAULT NULL COMMENT '投放时间',
    `findsEntityId`  varchar (100)  NULL DEFAULT NULL COMMENT 'finds实体ID',
    `status`  varchar (10)  NULL DEFAULT NULL COMMENT '车辆状态 normal-正常 scrap-报废',
    `sync_status`  varchar (10)  NULL DEFAULT 0 COMMENT '同步状态，0-未同步 1-已同步',
    `env` VARCHAR ( 10 ) NULL DEFAULT NULL COMMENT '环境标',
    `remark` VARCHAR ( 255 ) NULL DEFAULT NULL COMMENT '说明',
    `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted` BIGINT ( 20 ) NOT NULL DEFAULT 0 COMMENT '删除状态 0-未删除 1-已删除',
    PRIMARY KEY ( `id` ),
    UNIQUE KEY `UNI_BICYCLE` (`bicycle_number`),
    INDEX `IDX_ENTERPRISE_NO` ( `enterprise_no` ),
    INDEX `IDX_ENTERPRISE_NAME` ( `enterprise_name` ),
    INDEX `IDX_BICYCLE_NUMBER` ( `bicycle_number` ),
    INDEX `IDX_QR_CODE` ( `qr_code` )
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='企业车辆信息';


CREATE TABLE `ops_bicycle_use` (  
    `id` BIGINT ( 20 ) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增主键',
    `bicycle_number` varchar ( 100 ) NULL DEFAULT NULL COMMENT '车牌号',
    `control_manufacturer` varchar (200) NULL DEFAULT NULL COMMENT '中控厂商',
    `control_id` varchar (100) NULL DEFAULT NULL COMMENT '中控ID',
    `order_no` varchar ( 100 ) NULL DEFAULT NULL COMMENT '订单号',
    `ride_id` varchar ( 50 ) NULL DEFAULT NULL COMMENT '骑行ID',
    `enterprise_no` varchar ( 50 ) NULL DEFAULT NULL COMMENT '企业编号',
    `enterprise_name` varchar (100) NULL DEFAULT NULL COMMENT '企业名称',
  	`master_id` bigint(20) NULL default 0 comment '主用户ID',
    `enterprise_user_id` varchar (50) NULL DEFAULT NULL COMMENT '企业用户ID',
    `enterprise_user_name` varchar (50) NULL DEFAULT NULL COMMENT '企业用户姓名',
    `payment_id` varchar (50) NULL DEFAULT NULL COMMENT '支付ID',
    `unlock_time`  datetime  NULL DEFAULT NULL COMMENT '开锁时间',
    `lock_time`  datetime  NULL DEFAULT NULL COMMENT '落锁时间',
    `ride_mileage`  varchar (50) NULL DEFAULT NULL COMMENT '骑行里程',
    `mileage_unit`  varchar (20) NULL DEFAULT NULL COMMENT '里程单位',
    `sync_status`  varchar (10)  NULL DEFAULT 0 COMMENT '同步状态，0-未同步 1-已同步',
    `env` VARCHAR ( 10 ) NULL DEFAULT NULL COMMENT '环境标',
    `remark` VARCHAR ( 255 ) NULL DEFAULT NULL COMMENT '说明',
    `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted` BIGINT ( 20 ) NOT NULL DEFAULT 0 COMMENT '删除状态 0-未删除 1-已删除',
    PRIMARY KEY ( `id` ),
    UNIQUE KEY `UNI_RIDE_ID` (`ride_id`,`enterprise_no`),
    INDEX `IDX_ENTERPRISE_NO` ( `enterprise_no` ),
    INDEX `IDX_BICYCLE_NO` ( `bicycle_number` ),
    INDEX `IDX_RIDE_ID` ( `ride_id` ),
    INDEX `IDX_ORDER_NO` ( `order_no` ),
    INDEX `IDX_ENTERPRISE_USER_ID` ( `enterprise_user_id` )
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='车辆使用情况';

CREATE TABLE `ops_bicycle_use_detail` (  
    `id` BIGINT ( 20 ) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增主键',
    `ride_id` varchar ( 50 ) NULL DEFAULT NULL COMMENT '骑行ID',
    `use_type` varchar ( 20 ) NULL DEFAULT NULL COMMENT '使用类型，unlock-解锁 lock-落锁',
    `use_time`  datetime  NULL DEFAULT NULL COMMENT '开锁（落锁）时间',
    `point_name` varchar (200) NULL DEFAULT NULL COMMENT '起点(终点)名称',
    `point_longitude`  varchar (50) NULL DEFAULT NULL COMMENT '起点(终点)经度',
    `point_latitude`  varchar ( 50 ) NULL DEFAULT NULL COMMENT '起点(终点)纬度',  
    `point_height`  varchar ( 50 ) NULL DEFAULT NULL COMMENT '起点(终点)高程',  
    `sync_status`  varchar (10)  NULL DEFAULT 0 COMMENT '同步状态，0-未同步 1-已同步',
    `env` VARCHAR ( 10 ) NULL DEFAULT NULL COMMENT '环境标',
    `remark` VARCHAR ( 255 ) NULL DEFAULT NULL COMMENT '说明',
    `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted` BIGINT ( 20 ) NOT NULL DEFAULT 0 COMMENT '删除状态 0-未删除 1-已删除',
    PRIMARY KEY ( `id` ),
    INDEX `IDX_RIDE_ID` ( `ride_id` )
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='车辆使用详细信息';

CREATE TABLE `ops_order` (  
    `id` BIGINT ( 20 ) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增主键',
    `enterprise_no` varchar ( 50 ) NULL DEFAULT NULL COMMENT '企业编号',
    `order_no` varchar ( 50 ) NULL DEFAULT NULL COMMENT '订单号',
    `order_type`  varchar (50)   NULL DEFAULT NULL COMMENT '订单类型,new-订单 refund-退单',
    `enterprise_user_id` varchar (100) NULL DEFAULT NULL COMMENT '企业用户ID',
    `goods_describe`  varchar (50) NULL DEFAULT NULL COMMENT '商品描述 riding-骑行 card-购买卡',
    `order_time`  datetime NULL DEFAULT NULL COMMENT '下单时间',  
    `unlock_time`  datetime NULL DEFAULT NULL COMMENT '开锁时间',   
    `lock_time`  datetime NULL DEFAULT NULL COMMENT '落锁时间',
    `ride_mileage`  varchar (50) NULL DEFAULT NULL COMMENT '骑行里程',
    `mileage_unit`  varchar (20) NULL DEFAULT NULL COMMENT '里程单位',
    `order_amount`  varchar (20) NULL DEFAULT NULL COMMENT '订单金额',
    `need_pay_amount`  varchar (20) NULL DEFAULT NULL COMMENT '应付金额',
    `package_buy_order_no`  varchar (20) NULL DEFAULT NULL COMMENT '套餐卡购买订单号',
    `package_id`  varchar (20) NULL DEFAULT NULL COMMENT '套餐卡id',
    `zero_order_desc`  varchar (20) NULL DEFAULT NULL COMMENT '0元订单优惠描述',
    `env` VARCHAR ( 10 ) NULL DEFAULT NULL COMMENT '环境标',
    `remark` VARCHAR ( 255 ) NULL DEFAULT NULL COMMENT '说明',
    `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted` BIGINT ( 20 ) NOT NULL DEFAULT 0 COMMENT '删除状态 0-未删除 1-已删除',
    PRIMARY KEY ( `id` ),
    INDEX `IDX_ENTERPRISE_NO` ( `enterprise_no` ),
    INDEX `IDX_ORDER_NO` ( `order_no` )
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='骑行订单信息';

CREATE TABLE `ops_traffic_violation` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `violation_no` varchar(16) DEFAULT NULL COMMENT '违章单号',
  `violation_time` datetime DEFAULT NULL COMMENT '违章时间',
  `violation_type` varchar(64) DEFAULT NULL COMMENT '违章类型:no_car_license-无车牌, retrograde-逆行, nogo_fence-禁入, noout_fence-禁出, non_standard_park_fence-非规范停车, statistic_parking_fence-区域停车统计',
  `violation_entity` varchar(64) DEFAULT NULL COMMENT 'person 个人, company 公司',
  `violation_capture_urls` varchar(2047) DEFAULT NULL COMMENT '附件违章图片urls',
  `action_status` varchar(64) DEFAULT NULL COMMENT '状态: wait_audit-待确认, audit_sure-审核确认为违章单, had_sync-已同步, cancel-废弃',
  `riding_record_id` varchar(256) DEFAULT NULL COMMENT '骑行记录id',
  `enterprise_no` varchar(50) DEFAULT NULL COMMENT '企业code',
  `enterprise_name` varchar (100) NULL DEFAULT NULL COMMENT '企业名称',
  `master_id` bigint(20) NULL default 0 comment '主用户ID',
  `enterprise_user_id` varchar(100) DEFAULT NULL COMMENT '使用人用户id',
  `enterprise_user_name` varchar(100) DEFAULT NULL COMMENT '使用人姓名',
  `bicycle_no` varchar(256) DEFAULT NULL COMMENT '车牌号',
  `violation_location` varchar(256) DEFAULT NULL COMMENT '违章地点',
  `violation_latitude` varchar(256) DEFAULT NULL COMMENT '违章纬度',
  `violation_longitude` varchar(256) DEFAULT NULL COMMENT '违章经度',
  `fence_id` bigint(20) DEFAULT NULL COMMENT '围栏id',
  `fence_name` varchar(100) DEFAULT NULL COMMENT '围栏名称',
  `fence_max_client` int(11) DEFAULT NULL COMMENT '围栏容量',
  `fence_warn_clients` int(11) DEFAULT NULL COMMENT '围栏告警阀值',
  `fence_park_clients` int(11) DEFAULT NULL COMMENT '围栏实际停车数',
  `over_percent` varchar(30) DEFAULT NULL COMMENT '超出百分比',
  `create_user` varchar(50) DEFAULT NULL COMMENT '创建员工用户(system即为系统)',
  `modify_user` varchar(50) DEFAULT NULL COMMENT '修改员工用户(system即为系统)',
  `sync_status`  VARCHAR (10)  NULL DEFAULT 0 COMMENT '同步状态，0-未同步 1-已同步',
  `env` VARCHAR ( 10 ) NULL DEFAULT NULL COMMENT '环境标',
  `remark` VARCHAR ( 255 ) NULL DEFAULT NULL COMMENT '说明',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` BIGINT ( 20 ) NOT NULL DEFAULT 0 COMMENT '删除状态 0-未删除 1-已删除',
   PRIMARY KEY ( `id` ),
   INDEX `IDX_ENTERPRISE_NO` ( `enterprise_no` ),
   INDEX `IDX_ENTERPRISE_USER_ID` ( `enterprise_user_id` ),
   INDEX `IDX_RIDE_ID` ( `riding_record_id` )
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='衡阳-监管-违章单';

CREATE TABLE `ops_warning` (
  `id` BIGINT ( 20 ) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `warning_type` VARCHAR(64) NULL COMMENT '预警类型: exceed_warn 超过告警阀值, park_over 超过停车阀值',
  `enterprise_no` VARCHAR ( 50 ) NULL DEFAULT NULL COMMENT '企业编号',
  `enterprise_name` VARCHAR (100) NULL DEFAULT NULL COMMENT '企业名称',
  `fence_id` BIGINT ( 20 ) NULL DEFAULT NULL COMMENT '围栏id',
  `fence_max_client` INT(10) NULL DEFAULT NULL COMMENT '围栏容量(停放阈值)',
  `fence_warn_clients`     INT(10) COMMENT '围栏告警阀值',
  `fence_park_clients` INT ( 10 ) NULL DEFAULT NULL COMMENT '实际停放数量',
  `over_percent` VARCHAR ( 30 ) NULL DEFAULT NULL COMMENT '超出百分比',
  `sync_status`  VARCHAR (10)  NULL DEFAULT 0 COMMENT '同步状态，0-未同步 1-已同步',
  `env` VARCHAR ( 10 ) NULL DEFAULT NULL COMMENT '环境标',
  `remark` VARCHAR ( 255 ) NULL DEFAULT NULL COMMENT '说明',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` BIGINT ( 20 ) NOT NULL DEFAULT 0 COMMENT '删除状态 0-未删除 1-已删除',
  PRIMARY KEY ( `id` ),
  INDEX `IDX_ENTERPRISE_NO` ( `enterprise_no` ),
  INDEX `IDX_FENCE_ID` ( `fence_id` )
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='预警信息表';
```

##### 支付服务

```sql
CREATE TABLE `qx_payment` (
`id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '唯一id，自动生成',
`enterprise_user_id` varchar(50) NOT NULL COMMENT '企业用户id',
`master_id` bigint(20) NOT NULL COMMENT '主账户id',
`payment_no` varchar(128) DEFAULT NULL COMMENT '内部支付号',
`enterprise_code` varchar(32) NOT NULL COMMENT '企业编号',
`enterprise_name` varchar(32) NOT NULL COMMENT '企业名称',
`status` int(2) NOT NULL COMMENT '支付的状态，1,收到通知；2,确认支付 3,关闭 9,支付完成',
`callback_status` varchar(16) NOT NULL DEFAULT 'new' COMMENT '支付回调状态: new-未执行, done-处理完成',
`callback_retry_times` int(11) DEFAULT 0 COMMENT '支付回调重试次数',
`total_amount` decimal(20,2) DEFAULT NULL COMMENT '该笔支付的金额',
`real_amount` decimal(20,2) DEFAULT NULL COMMENT '该笔支付实收费用',
`order_no` varchar(128) DEFAULT NULL COMMENT '该笔支付关联的订单号',
`withdrawal_no` varchar(128) DEFAULT NULL COMMENT '该笔支付关联的提现流水号',
`type` int(11) DEFAULT NULL COMMENT '支付类型，1.支付宝收款,2.银行收款,3.银行提现,4.支付宝退款,5.银行退款,6微信支付，7微信退款，8个人快捷支付，9企业网银支付，10支付宝网银个人，11支付宝网银企业',
`pay_time` datetime DEFAULT NULL COMMENT '支付时间',
`payment_no_bank` varchar(128) DEFAULT NULL COMMENT '交易关联的银行流水号(唯一)',
`payment_no_weixin` varchar(128) DEFAULT NULL COMMENT '交易关联的微信流水号(唯一)',
`payment_no_alipay` varchar(128) DEFAULT NULL COMMENT '交易关联的支付宝流水号(唯一)',
`payment_no_quick_pay` varchar(128) DEFAULT NULL COMMENT '交易关联的快捷支付流水号(唯一)',
`prepay_id` varchar(128) DEFAULT NULL COMMENT '调用微信统一下单接口是生成的预支付id(唯一)',
`partern_account` varchar(128) DEFAULT NULL COMMENT '该笔支付对方的账号',
`partern_name` varchar(255) DEFAULT NULL COMMENT '该笔支付对方的账户名称',
`version` int(2) DEFAULT '1' COMMENT '支付记录版本号',
`seller` varchar(128) DEFAULT NULL COMMENT '收款账户',
`trade_no` varchar(50) DEFAULT NULL COMMENT '交易流水号',
`bu_code` varchar(32) DEFAULT NULL COMMENT 'BUCode',
`channel` int(11) DEFAULT NULL COMMENT '支付渠道',
`payer_type` tinyint(3) unsigned DEFAULT NULL COMMENT '付款方用户类型. 1:个人;2:企业;',
`intervention_flag` varchar(16) DEFAULT NULL COMMENT '人工干预标识: yes-是 no-否',
`is_deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标志:0-未删除,1-已删除',
`frozen_ext_info` varchar(128) DEFAULT NULL COMMENT 'json字段，包含frozenType,frozenUsage,operatorRole,description',
`frozen_fund_no` varchar(32) DEFAULT NULL COMMENT '资金冻结号',
`frozen_status` varchar(32) DEFAULT NULL COMMENT '冻结状态:need_frozen 待申请冻结,frozen 已冻结,unfreeze_refund 解冻退回余额, unfreeze_paid 解冻支付',
`exclusive_bank_acc_no` varchar(128) DEFAULT NULL COMMENT '专属银行账号',
`payer_bank_name` varchar(128) DEFAULT NULL COMMENT '付款方银行名称',
`outer_payment_no` varchar(128) DEFAULT NULL COMMENT '外部支付号',
`already_refund_amount` decimal(20,2) DEFAULT '0.00' COMMENT '已经退款金额',
`refund_status` varchar(32) DEFAULT 'no_refund' COMMENT '退款状态，未退款：no_refund，部分退款：part_refund，全部已退款：all_refund',
`trade_success_time` datetime DEFAULT NULL COMMENT '交易成功时间',
`trade_verify_retry` tinyint(4) DEFAULT '0' COMMENT '交易重试次数',
`hb_conf_code` varchar(128) DEFAULT NULL COMMENT '花呗分期配置code',
`biz_owner_id` bigint(20) DEFAULT NULL COMMENT '业务单归属人id',
`env` varchar(16) DEFAULT NULL COMMENT '环境',
`comment` varchar(255) DEFAULT NULL COMMENT '备注信息',
`ext` varchar(255) DEFAULT NULL COMMENT '扩展信息',
`gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
`gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
PRIMARY KEY (`id`),
UNIQUE KEY `payment_no` (`payment_no`),
UNIQUE KEY `payment_no_bank` (`payment_no_bank`),
UNIQUE KEY `payment_no_alipay` (`payment_no_alipay`),
UNIQUE KEY `payment_no_weixin` (`payment_no_weixin`),
KEY `idx_order_no` (`order_no`),
KEY `idx_enterprise_user_id` (`enterprise_user_id`),
KEY `idx_trade_no` (`trade_no`),
KEY `idx_seller` (`seller`),
KEY `idx_gmt_create` (`gmt_create`,`comment`),
KEY `idx_comment_gmt_create` (`comment`,`gmt_create`),
KEY `idx_pay_time` (`pay_time`),
KEY `idx_outerpaymentno` (`outer_payment_no`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='支付表';


CREATE TABLE `payment_alipay_configure` (
`id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '支付id',
`merchant_code` varchar(255) NOT NULL COMMENT '商户代码',
`partner` varchar(255) NOT NULL COMMENT '合作身份者ID，签约账号',
`seller_id` varchar(255) NOT NULL COMMENT '收款支付宝账号',
`sign_key` varchar(255) NOT NULL COMMENT 'MD5签名密钥',
`app_id` varchar(255) NOT NULL COMMENT '应用id',
`private_key` varchar(4096) NOT NULL COMMENT '应用私钥',
`public_key` varchar(2048) NOT NULL COMMENT '支付宝公钥',
`salt` varchar(16) NOT NULL COMMENT 'salt',
`callback` varchar(255) NOT NULL COMMENT '支付回调地址',
`callback_ebank` varchar(255) NOT NULL COMMENT '网银回调url',
`state` int(11) NOT NULL COMMENT '状态, 1:启用,-1:禁用',
`comment` varchar(255) NOT NULL COMMENT '备注',
`gmt_create` datetime NOT NULL COMMENT '创建时间',
`gmt_modified` datetime NOT NULL COMMENT '修改时间',
`is_deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标志:0-未删除,1-已删除',
`sign_type` varchar(20) DEFAULT NULL COMMENT '签名方式',
`api_version` varchar(32) DEFAULT NULL COMMENT 'API版本',
PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='支付宝支付配置表';


CREATE TABLE `mars_merchant` (
`id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '唯一id，自动生成',
`code` varchar(50) DEFAULT NULL COMMENT '商户code唯一',
`name` varchar(80) DEFAULT NULL COMMENT '商户名称',
`bank_account` varchar(50) DEFAULT NULL COMMENT '商户银行账号',
`bank_name` varchar(80) DEFAULT NULL COMMENT '商户银行账号姓名',
`branch_bank` varchar(80) DEFAULT NULL COMMENT '商户银行分行名',
`alipay_account` varchar(50) DEFAULT NULL COMMENT '支付宝账号',
`alipay_name` varchar(50) DEFAULT NULL COMMENT '支付宝账号姓名',
`wechat_account` varchar(50) DEFAULT NULL COMMENT '微信账号',
`wechat_name` varchar(50) DEFAULT NULL COMMENT '微信账号姓名',
`sort` int(10) DEFAULT NULL COMMENT '排序值 1-100 数值越大越靠前',
`comment` varchar(100) DEFAULT NULL COMMENT '备注信息',
`status` int(2) DEFAULT NULL COMMENT '商户状态：1:未删除 2:删除',
`gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
`gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
`invoice_bank` varchar(128) DEFAULT NULL COMMENT '发票开户银行',
`invoice_bank_account` varchar(64) DEFAULT NULL COMMENT '发票开户银行账号',
`creator` varchar(45) DEFAULT NULL COMMENT '创建者',
`version` int(11) DEFAULT '1' COMMENT '乐观锁',
`taxpayer_id` varchar(32) DEFAULT NULL COMMENT '纳税人识别号',
`address` varchar(64) DEFAULT NULL COMMENT '地址',
`telephone` varchar(16) DEFAULT NULL COMMENT '电话',
`payee` varchar(16) DEFAULT NULL COMMENT '付款人',
`reviewer` varchar(16) DEFAULT NULL COMMENT '复核人',
`terminal_no` varchar(32) DEFAULT NULL COMMENT '终端编号',
`paper_invoice_apply` tinyint(1) DEFAULT NULL COMMENT '纸质发票申请标识',
`digit_invoice_apply` tinyint(1) DEFAULT NULL COMMENT '电子发票申请标识',
`invoice_amount_limit` decimal(20,0) DEFAULT NULL COMMENT '开票限额',
`is_deleted` tinyint(4) DEFAULT '0' COMMENT '删除标记, 0 - 有效，1-删除',
`env` varchar(32) DEFAULT 'pro' COMMENT '环境标',
PRIMARY KEY (`id`),
KEY `idx_code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='mars(产品商品计价)商户信息表';


alter table ops_traffic_violation add column master_id bigint(20) not null default 0 comment '主用户ID';

alter table ops_bicycle_use add column master_id bigint(20) not null default 0 comment '主用户ID';
```

##### 二、DML

```sql
delete from ops_enterprise;
delete from ops_enterprise_employee;
delete from ops_enterprise_bicycle;
delete from ops_user;
delete from ops_enterprise_user;

INSERT INTO ops_enterprise
(enterprise_no, enterprise_name, enterprise_telephone, enterprise_address, legal_person, social_credit_code, registered_capital, registered_time, enterprise_type, enterprise_status, pay_callback_url, data_sync_url, rsa_public_key, sync_status, env, remark, gmt_create, gmt_modified, is_deleted)
VALUES('zhcx', '智慧出行', '073412345678', '', '陈贤文', '91430400MA7AU6316Q', '1000', '2021-10-12', 'limited', 'enable', NULL, NULL, NULL, '0', 'hypro', NULL, '2021-12-06 13:19:34.0', '2021-12-06 13:19:34.0', 0);

INSERT INTO ops_enterprise_employee
(enterprise_no, enterprise_name, employee_name, area, `position`, telephone, duty, entry_time, sex, status, deparment, sync_status, env, remark, gmt_create, gmt_modified, is_deleted)
VALUES('zhcx', '智慧出行', '测试员工1', '高新区', '主管', '13811111111', '', '2021-11-12', 'male', '在职', NULL, 0, 'hypro', NULL, '2021-12-06 13:19:34.0', '2021-12-06 13:19:34.0', 0);

INSERT INTO ops_enterprise_employee
(enterprise_no, enterprise_name, employee_name, area, `position`, telephone, duty, entry_time, sex, status, deparment, sync_status, env, remark, gmt_create, gmt_modified, is_deleted)
VALUES('zhcx', '智慧出行', '测试员工2', '高新区', '协管', '13811111112', '', '2021-11-12', 'male', '在职', NULL, 0, 'hypro', NULL, '2021-12-06 13:19:34.0', '2021-12-06 13:19:34.0', 0);

INSERT INTO ops_enterprise_employee
(enterprise_no, enterprise_name, employee_name, area, `position`, telephone, duty, entry_time, sex, status, deparment, sync_status, env, remark, gmt_create, gmt_modified, is_deleted)
VALUES('zhcx', '智慧出行', '测试员工3', '高新区', '协管', '13811111113', '', '2021-11-12', 'male', '在职', NULL, 0, 'hypro', NULL, '2021-12-06 13:19:34.0', '2021-12-06 13:19:34.0', 0);

INSERT INTO ops_enterprise_bicycle
(enterprise_no, enterprise_name, control_manufacturer, control_id, bicycle_number, qr_code, qx_account, produce_time, launch_time, findsEntityId, status, sync_status, env, remark, gmt_create, gmt_modified, is_deleted)
VALUES('zhcx', '智慧出行', '小安', '009265648', '衡阳009361', '0001', '', '2021.10.15', '2021.12.1', NULL, 'normal', '0', 'hypro', NULL, '2021-12-06 13:19:34.0', '2021-12-06 13:19:34.0', 0);

INSERT INTO ops_enterprise_bicycle
(enterprise_no, enterprise_name, control_manufacturer, control_id, bicycle_number, qr_code, qx_account, produce_time, launch_time, findsEntityId, status, sync_status, env, remark, gmt_create, gmt_modified, is_deleted)
VALUES('zhcx', '智慧出行', '小安', '009265649', '衡阳009362', '0002', '', '2021.10.15', '2021.12.1', NULL, 'normal', '0', 'hypro', NULL, '2021-12-06 13:19:34.0', '2021-12-06 13:19:34.0', 0);

INSERT INTO ops_enterprise_bicycle
(enterprise_no, enterprise_name, control_manufacturer, control_id, bicycle_number, qr_code, qx_account, produce_time, launch_time, findsEntityId, status, sync_status, env, remark, gmt_create, gmt_modified, is_deleted)
VALUES('zhcx', '智慧出行', '小安', '009265650', '衡阳009363', '0003', '', '2021.10.15', '2021.12.1', NULL, 'normal', '0', 'hypro', NULL, '2021-12-06 13:19:34.0', '2021-12-06 13:19:34.0', 0);

INSERT INTO ops_user
(id, name, id_no, sex, sync_status, env, remark, gmt_create, gmt_modified, is_deleted)
VALUES(20, '张三', '43040019920901078X', 'male', '0', 'hypro', '', '2021-12-06 13:19:34.0', '2021-12-06 13:19:34.0', 0);

INSERT INTO ops_enterprise_user
(enterprise_no, enterprise_name, master_id, enterprise_user_id, nick_name, telephone, registered_city, registered_channel, registered_time, status, sync_status, env, remark, gmt_create, gmt_modified, is_deleted)
VALUES('zhcx', '智慧出行', 20, 'zhcx_01', '张三', '123456789012', '衡阳', '微信小程序', '2021-12-1', 'normal', '0', 'hypro', '', '2021-12-06 13:19:34.0', '2021-12-06 13:19:34.0', 0);

INSERT INTO ops_user
(id, name, id_no, sex, sync_status, env, remark, gmt_create, gmt_modified, is_deleted)
VALUES(21, '李四', '430400199511110567', 'male', '0', 'hypro', '', '2021-12-06 13:19:34.0', '2021-12-06 13:19:34.0', 0);

INSERT INTO ops_enterprise_user
(enterprise_no, enterprise_name, master_id, enterprise_user_id, nick_name, telephone, registered_city, registered_channel, registered_time, status, sync_status, env, remark, gmt_create, gmt_modified, is_deleted)
VALUES('zhcx', '智慧出行', 21, 'zhcx_02', '李四', '123456789012', '衡阳', '微信小程序', '2021-12-1', 'normal', '0', 'hypro', '', '2021-12-06 13:19:34.0', '2021-12-06 13:19:34.0', 0);
```

#### 