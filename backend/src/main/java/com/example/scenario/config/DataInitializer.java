package com.example.scenario.config;

import com.example.scenario.entity.*;
import com.example.scenario.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private AccidentDataRepository accidentDataRepository;

    @Autowired
    private SceneDesignRepository sceneDesignRepository;

    @Autowired
    private DangerSceneRepository dangerSceneRepository;

    @Autowired
    private TestCaseRepository testCaseRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private ProcessTaskRepository processTaskRepository;

    @Autowired
    private CaseDesignRepository caseDesignRepository;

    @Autowired
    private DataSourceRepository dataSourceRepository;

    @Autowired
    private ApprovalFlowRepository approvalFlowRepository;

    @Autowired
    private SystemSettingRepository systemSettingRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void run(String... args) {
        initAccidentData();
        initSceneDesign();
        initDangerScene();
        initTestCase();
        initTags();
        initProcessTasks();
        initCaseDesign();
        initDataSource();
        initApprovalFlows();
        initSystemSettings();
    }

    // ==================== AccidentData ====================

    private void initAccidentData() {
        if (accidentDataRepository.count() == 0) {
            accidentDataRepository.saveAll(Arrays.asList(
                createAccidentData("DC001", "雨天高速公路多车碰撞事故", "张晓明", "2023-09-10", "2023-09-09",
                    "G15沈海高速", "高速公路", "大雨", "Model 3", "Autopilot", "事故", "重大", "待审核"),
                createAccidentData("DC002", "城市路口非机动车碰撞事故", "李思琪", "2023-09-08", "2023-09-07",
                    "北京市朝阳区建国路", "城市道路", "晴天", "蔚来ES6", "NAD 2.0", "事故", "一般", "已通过"),
                createAccidentData("DC003", "夜间隧道追尾事故", "王建国", "2023-09-06", "2023-09-05",
                    "秦岭终南山隧道", "隧道", "阴天", "小鹏P7", "XPILOT 4.0", "事故", "较大", "待审核"),
                createAccidentData("DC004", "山区道路弯道超车事故", "赵海洋", "2023-09-04", "2023-09-03",
                    "云南省丽江市山路", "山区道路", "小雨", "理想L9", "NOA 3.0", "事故", "重大", "已驳回"),
                createAccidentData("DC005", "十字路口鬼探头场景", "孙东方", "2023-09-10", "2023-09-09",
                    "G15沈海高速", "高速公路", "晴", "Model 3", "Autopilot", "事故", "重大", "设计完成")
            ));
        }
    }

    private AccidentData createAccidentData(String id, String name, String reporter, String reportTime,
                                            String occurTime, String location, String roadType, String weather,
                                            String vehicleType, String systemVersion, String accidentType,
                                            String accidentLevel, String status) {
        AccidentData data = new AccidentData();
        data.setId(id);
        data.setName(name);
        data.setReporter(reporter);
        data.setReportTime(LocalDate.parse(reportTime));
        data.setOccurTime(LocalDate.parse(occurTime));
        data.setLocation(location);
        data.setRoadType(roadType);
        data.setWeather(weather);
        data.setVehicleType(vehicleType);
        data.setSystemVersion(systemVersion);
        data.setAccidentType(accidentType);
        data.setAccidentLevel(accidentLevel);
        data.setStatus(status);
        return data;
    }

    // ==================== SceneDesign ====================

    private void initSceneDesign() {
        if (sceneDesignRepository.count() == 0) {
            sceneDesignRepository.saveAll(Arrays.asList(
                createSceneDesign("SC001", "雨天高速公路多车碰撞场景", "刘设计",
                    "雨天,高速公路,多车碰撞", "王工,李工", "待修改", 0, "2023-09-12",
                    "DC001", "高速场景", "高", "scenariocase"),
                createSceneDesign("SC002", "城市路口非机动车鬼探头场景", "陈设计",
                    "城市道路,交叉口,非机动车", "王工,李工", "已修改 2 处", 2, "2023-09-10",
                    "DC002", "城市道路", "严重", "scenariocase"),
                createSceneDesign("SC003", "夜间隧道追尾场景", "张设计",
                    "夜间,隧道,追尾", "王工,赵工", "设计中", 0, "2023-09-08",
                    "DC003", "隧道", "中", "scenariocase"),
                createSceneDesign("SC004", "山区道路弯道障碍物避让场景", "李设计",
                    "山区道路,弯道,障碍物", "王工,孙工", "设计中", 0, "2023-09-06",
                    "DC004", "山区道路", "高", "scenariocase"),
                createSceneDesign("SC005", "雾天高速公路能见度测试场景", "刘设计",
                    "雾天,高速公路,能见度", "王工,李工", "审核中", 0, "2023-09-11",
                    "DC005", "高速场景", "中", "scenariocase"),
                createSceneDesign("SC006", "城市快速路紧急变道场景", "陈设计",
                    "城市道路,快速路,变道", "王工,赵工", "待验证", 0, "2023-09-09",
                    "DC006", "城市道路", "高", "scenariocase"),
                createSceneDesign("SC007", "乡村道路行人横穿场景", "张设计",
                    "乡村道路,行人,横穿", "王工,孙工", "修改中", 2, "2023-09-07",
                    "DC007", "乡村道路", "严重", "scenariocase"),
                createSceneDesign("SC008", "停车场倒车入库场景", "李设计",
                    "停车场,倒车,入库", "王工,李工", "设计中", 0, "2023-09-05",
                    "DC008", "停车场", "低", "scenariocase")
            ));
        }
    }

    private SceneDesign createSceneDesign(String id, String name, String designer, String tags,
                                          String verifiers, String status, Integer modifyCount, String createTime,
                                          String dcid, String type, String level, String stage) {
        SceneDesign design = new SceneDesign();
        design.setId(id);
        design.setName(name);
        design.setDesigner(designer);
        design.setTags(tags);
        design.setVerifiers(verifiers);
        design.setStatus(status);
        design.setModifyCount(modifyCount);
        design.setCreateTime(LocalDate.parse(createTime));
        design.setDcid(dcid);
        design.setType(type);
        design.setLevel(level);
        design.setStage(stage);
        return design;
    }

    // ==================== DangerScene ====================

    private void initDangerScene() {
        if (dangerSceneRepository.count() == 0) {
            dangerSceneRepository.saveAll(Arrays.asList(
                createDangerScene("SC001", "v1.0", "雨天高速公路多车碰撞场景",
                    "雨天环境下高速公路多车连环碰撞的危险场景", "雨天,高速公路,多车碰撞", "高", "事故数据", 5, "2023-09-15"),
                createDangerScene("SC002", "v1.2", "城市路口非机动车鬼探头场景",
                    "城市路口非机动车突然出现导致碰撞风险场景", "城市道路,交叉口,非机动车", "严重", "事故数据", 8, "2023-09-14"),
                createDangerScene("SC003", "v1.1", "夜间隧道追尾场景",
                    "夜间隧道内视线受限导致追尾事故场景", "夜间,隧道,追尾", "中", "仿真生成", 3, "2023-09-13"),
                createDangerScene("SC004", "v1.0", "山区道路弯道障碍物避让场景",
                    "山区弯道突发障碍物需要紧急避让场景", "山区道路,弯道,障碍物", "高", "事故数据", 6, "2023-09-12")
            ));
        }
    }

    private DangerScene createDangerScene(String id, String version, String name, String description,
                                          String tags, String level, String source, Integer caseCount, String storageTime) {
        DangerScene scene = new DangerScene();
        scene.setId(id);
        scene.setVersion(version);
        scene.setName(name);
        scene.setDescription(description);
        scene.setTags(tags);
        scene.setLevel(level);
        scene.setSource(source);
        scene.setCaseCount(caseCount);
        scene.setStorageTime(LocalDate.parse(storageTime));
        return scene;
    }

    // ==================== TestCase ====================

    private void initTestCase() {
        if (testCaseRepository.count() == 0) {
            testCaseRepository.saveAll(Arrays.asList(
                createTestCase("TC001", "SC001", "雨天高速公路多车碰撞测试用例", "实车测试", "已完成", "v1.0", "2023-09-16"),
                createTestCase("TC002", "SC002", "城市路口非机动车鬼探头测试用例", "实车测试", "进行中", "v1.1", "2023-09-15"),
                createTestCase("TC003", "SC003", "夜间隧道追尾测试用例", "仿真测试", "待审核", "v1.0", "2023-09-14"),
                createTestCase("TC004", "SC004", "山区道路弯道障碍物避让测试用例", "实车测试", "已完成", "v1.2", "2023-09-13")
            ));
        }
    }

    private TestCase createTestCase(String id, String sceneId, String name, String type,
                                    String status, String version, String createTime) {
        TestCase testCase = new TestCase();
        testCase.setId(id);
        testCase.setSceneId(sceneId);
        testCase.setName(name);
        testCase.setType(type);
        testCase.setStatus(status);
        testCase.setVersion(version);
        testCase.setCreateTime(LocalDate.parse(createTime));
        return testCase;
    }

    // ==================== Tags ====================

    private void initTags() {
        if (tagRepository.count() == 0) {
            tagRepository.saveAll(Arrays.asList(
                createTag("危险场景类别", "多车碰撞"),
                createTag("危险场景类别", "追尾"),
                createTag("危险场景类别", "鬼探头"),
                createTag("危险场景类别", "障碍物避让"),
                createTag("危险场景类别", "超车"),
                createTag("交通参与者", "非机动车"),
                createTag("交通参与者", "行人"),
                createTag("交通参与者", "机动车"),
                createTag("交通参与者", "骑行者"),
                createTag("道路类型", "高速公路"),
                createTag("道路类型", "城市道路"),
                createTag("道路类型", "山区道路"),
                createTag("道路类型", "隧道"),
                createTag("道路类型", "交叉口"),
                createTag("天气条件", "晴天"),
                createTag("天气条件", "雨天"),
                createTag("天气条件", "雪天"),
                createTag("天气条件", "雾天"),
                createTag("天气条件", "阴天"),
                createTag("时段", "白天"),
                createTag("时段", "夜间"),
                createTag("时段", "黄昏"),
                createTag("时段", "凌晨")
            ));
        }
    }

    private Tag createTag(String category, String name) {
        Tag tag = new Tag();
        tag.setCategory(category);
        tag.setName(name);
        return tag;
    }

    // ==================== ProcessTasks ====================

    private void initProcessTasks() {
        if (processTaskRepository.count() == 0) {
            processTaskRepository.saveAll(Arrays.asList(
                createProcessTask("雨天高速公路多车碰撞场景", "数据筛选审核", "张晓明", LocalDateTime.of(2023, 9, 15, 14, 30, 0), "待审核"),
                createProcessTask("雨天高速公路非机动车鬼探头场景", "数据筛选审核", "张晓明", LocalDateTime.of(2023, 9, 14, 10, 15, 0), "待审核"),
                createProcessTask("城市路口非机动车鬼探头场景", "场景设计", "李思琪", LocalDateTime.of(2023, 9, 14, 10, 15, 0), "设计中"),
                createProcessTask("山区道路弯道障碍物避让场景", "场景验证", "王建国", LocalDateTime.of(2023, 9, 13, 16, 45, 0), "待验证"),
                createProcessTask("高速追尾事故场景分析", "用例设计", "赵海洋", LocalDateTime.of(2023, 9, 12, 9, 20, 0), "已完成")
            ));
        }
    }

    private ProcessTask createProcessTask(String name, String stage, String submitter,
                                          LocalDateTime submitTime, String status) {
        ProcessTask task = new ProcessTask();
        task.setName(name);
        task.setStage(stage);
        task.setSubmitter(submitter);
        task.setSubmitTime(submitTime);
        task.setStatus(status);
        return task;
    }

    // ==================== CaseDesign ====================

    private void initCaseDesign() {
        if (caseDesignRepository.count() == 0) {
            caseDesignRepository.saveAll(Arrays.asList(
                createCaseDesign("SC001", "雨天高速公路多车碰撞场景", "赵测试 / 王评价", 75, "2023-09-16"),
                createCaseDesign("SC002", "城市路口非机动车鬼探头场景", "李测试 / 王评价", 30, "2023-09-17"),
                createCaseDesign("SC003", "夜间隧道追尾场景", "张测试 / 赵评价", 90, "2023-09-15"),
                createCaseDesign("SC004", "山区道路弯道障碍物避让场景", "王测试 / 孙评价", 45, "2023-09-14")
            ));
        }
    }

    private CaseDesign createCaseDesign(String id, String name, String responsible,
                                        Integer progress, String createTime) {
        CaseDesign caseDesign = new CaseDesign();
        caseDesign.setId(id);
        caseDesign.setName(name);
        caseDesign.setResponsible(responsible);
        caseDesign.setProgress(progress);
        caseDesign.setCreateTime(LocalDate.parse(createTime));
        return caseDesign;
    }

    // ==================== DataSource ====================

    private void initDataSource() {
        if (dataSourceRepository.count() == 0) {
            dataSourceRepository.saveAll(Arrays.asList(
                createDataSource("主机厂内部事故事件", "启用"),
                createDataSource("部委监管数据", "启用"),
                createDataSource("深度调查机构数据", "启用"),
                createDataSource("司法鉴定机构数据", "启用"),
                createDataSource("智驾事故研判数据", "启用")
            ));
        }
    }

    private DataSource createDataSource(String name, String status) {
        DataSource dataSource = new DataSource();
        dataSource.setName(name);
        dataSource.setStatus(status);
        return dataSource;
    }

    // ==================== ApprovalFlows ====================

    @SuppressWarnings("unchecked")
    private void initApprovalFlows() {
        if (approvalFlowRepository.count() == 0) {
            // Scene approval flows (SC001-SC008)
            saveApprovalFlow("SC001", "scene", "审核中", buildFlowData(
                buildStep("设计中", "designing", "completed", "刘设计", "2023-09-12 09:00", "开始设计", "场景设计初稿完成", Collections.emptyList()),
                buildStep("审核中", "reviewing", "active", "王工", "2023-09-13 14:30", "提交审核", "",
                    buildModification("陈设计", "2023-09-13 16:00", "修改了场景描述部分",
                        buildTextChange("测试场景", new Map[]{
                            mapOf("type", "equal", "text", "试验道路为至少包含由四条双向两车道的长直城区道路连接的丁字交叉口，道路设计速度为60km/h，"),
                            mapOf("type", "delete", "text", "天气晴朗，", "author", "陈设计", "time", "2023-09-13 16:00"),
                            mapOf("type", "add", "text", "雨天路面湿滑，能见度降低，", "author", "陈设计", "time", "2023-09-13 16:00"),
                            mapOf("type", "equal", "text", "道路边界线为白实线。路口设置停止线，停止线后方可设置人行横道。")
                        })
                    )
                ),
                buildStep("待验证", "pending_validation", "pending", "", "", "", "", Collections.emptyList()),
                buildStep("已完成", "completed", "pending", "", "", "", "", Collections.emptyList())
            ));
            saveApprovalFlow("SC002", "scene", "已完成", buildFlowData(
                buildStep("设计中", "designing", "completed", "陈设计", "2023-09-10 08:30", "开始设计", "城市路口非机动车鬼探头场景设计完成", Collections.emptyList()),
                buildStep("审核中", "reviewing", "completed", "王工", "2023-09-11 10:00", "提交审核", "审核通过",
                    buildModification("李设计", "2023-09-11 14:15", "调整了交叉口场景的描述",
                        buildTextChange("测试场景", new Map[]{
                            mapOf("type", "equal", "text", "十字路口右侧支路的道路右侧有一静止成人，成人所处位置距离最右侧车道中心线5m。"),
                            mapOf("type", "delete", "text", "交叉口未设置信号灯。", "author", "李设计", "time", "2023-09-11 14:15"),
                            mapOf("type", "add", "text", "交叉口未设置信号灯或设置信号灯但处于全灭状态。", "author", "李设计", "time", "2023-09-11 14:15")
                        }),
                        buildTextChange("场景描述", new Map[]{
                            mapOf("type", "equal", "text", "道路边界线为白实线，"),
                            mapOf("type", "delete", "text", "对向车流分隔线为黄实线。", "author", "李设计", "time", "2023-09-11 14:15"),
                            mapOf("type", "add", "text", "对向车流分隔线为黄实线单线或双线，同向车流分隔线为白虚线。", "author", "李设计", "time", "2023-09-11 14:15")
                        })
                    )
                ),
                buildStep("待验证", "pending_validation", "completed", "赵工", "2023-09-12 09:00", "提交验证", "验证通过",
                    buildModification("陈设计", "2023-09-12 11:30", "根据验证反馈调整了行人速度参数",
                        buildTextChange("测试场景", new Map[]{
                            mapOf("type", "equal", "text", "当被测车辆距与成人的预测冲突点的预计行驶时间到达3s时，假人从静止位置以"),
                            mapOf("type", "delete", "text", "5km/h", "author", "陈设计", "time", "2023-09-12 11:30"),
                            mapOf("type", "add", "text", "6km/h", "author", "陈设计", "time", "2023-09-12 11:30"),
                            mapOf("type", "equal", "text", "的速度横穿道路。")
                        })
                    )
                ),
                buildStep("已完成", "completed", "completed", "赵工", "2023-09-12 15:00", "验证通过", "场景设计已完成", Collections.emptyList())
            ));
            saveApprovalFlow("SC003", "scene", "设计中", buildFlowData(
                buildStep("设计中", "designing", "active", "张设计", "2023-09-08 09:00", "开始设计", "夜间隧道追尾场景设计进行中", Collections.emptyList()),
                buildStep("审核中", "reviewing", "pending", "", "", "", "", Collections.emptyList()),
                buildStep("待验证", "pending_validation", "pending", "", "", "", "", Collections.emptyList()),
                buildStep("已完成", "completed", "pending", "", "", "", "", Collections.emptyList())
            ));
            saveApprovalFlow("SC004", "scene", "设计中", buildFlowData(
                buildStep("设计中", "designing", "active", "李设计", "2023-09-06 10:00", "开始设计", "山区道路弯道障碍物避让场景设计进行中", Collections.emptyList()),
                buildStep("审核中", "reviewing", "pending", "", "", "", "", Collections.emptyList()),
                buildStep("待验证", "pending_validation", "pending", "", "", "", "", Collections.emptyList()),
                buildStep("已完成", "completed", "pending", "", "", "", "", Collections.emptyList())
            ));
            saveApprovalFlow("SC005", "scene", "审核中", buildFlowData(
                buildStep("设计中", "designing", "completed", "刘设计", "2023-09-11 08:00", "开始设计", "雾天高速公路能见度测试场景设计完成", Collections.emptyList()),
                buildStep("审核中", "reviewing", "active", "王工", "2023-09-12 10:30", "提交审核", "",
                    buildModification("刘设计", "2023-09-12 15:00", "补充了雾天能见度等级划分参数",
                        buildTextChange("封闭场地环境", new Map[]{
                            mapOf("type", "equal", "text", "雾天能见度等级分为"),
                            mapOf("type", "delete", "text", "1级（200m＜能见度≤500m）、2级（100m＜能见度≤200m）", "author", "刘设计", "time", "2023-09-12 15:00"),
                            mapOf("type", "add", "text", "1级（200m＜能见度≤500m）、2级（100m＜能见度≤200m）、3级（50m＜能见度≤100m）、4级（能见度≤50m）", "author", "刘设计", "time", "2023-09-12 15:00"),
                            mapOf("type", "equal", "text", "；")
                        })
                    )
                ),
                buildStep("待验证", "pending_validation", "pending", "", "", "", "", Collections.emptyList()),
                buildStep("已完成", "completed", "pending", "", "", "", "", Collections.emptyList())
            ));
            saveApprovalFlow("SC006", "scene", "待验证", buildFlowData(
                buildStep("设计中", "designing", "completed", "陈设计", "2023-09-09 08:00", "开始设计", "城市快速路紧急变道场景设计完成", Collections.emptyList()),
                buildStep("审核中", "reviewing", "completed", "王工", "2023-09-10 14:00", "提交审核", "审核通过",
                    buildModification("陈设计", "2023-09-10 16:30", "优化了变道场景的描述",
                        buildTextChange("测试场景", new Map[]{
                            mapOf("type", "equal", "text", "城市快速路上，被测车辆以"),
                            mapOf("type", "add", "text", "60km/h", "author", "陈设计", "time", "2023-09-10 16:30"),
                            mapOf("type", "equal", "text", "速度行驶，前方车辆突然减速，被测车辆需要紧急变道避让。")
                        })
                    )
                ),
                buildStep("待验证", "pending_validation", "active", "赵工", "2023-09-11 09:00", "提交验证", "",
                    buildModification("陈设计", "2023-09-11 14:00", "根据验证反馈调整了变道距离参数",
                        buildTextChange("泛化要素-横穿距离", new Map[]{
                            mapOf("type", "delete", "text", "[5,7]m", "author", "陈设计", "time", "2023-09-11 14:00"),
                            mapOf("type", "add", "text", "[3,8]m", "author", "陈设计", "time", "2023-09-11 14:00")
                        })
                    )
                )
            ));
            saveApprovalFlow("SC007", "scene", "修改中", buildFlowData(
                buildStep("设计中", "designing", "completed", "张设计", "2023-09-07 08:00", "开始设计", "乡村道路行人横穿场景设计完成", Collections.emptyList()),
                buildStep("审核中", "reviewing", "completed", "王工", "2023-09-08 10:00", "提交审核", "审核通过",
                    buildModification("张设计", "2023-09-08 14:00", "补充了行人类型参数（成人/儿童）",
                        buildTextChange("泛化要素-行人类型", new Map[]{
                            mapOf("type", "equal", "text", "场景泛化至少应考虑要素：天气变化、光线变化、"),
                            mapOf("type", "add", "text", "行人类型变化（成人/儿童）及", "author", "张设计", "time", "2023-09-08 14:00"),
                            mapOf("type", "equal", "text", "假人速度变化。")
                        })
                    )
                ),
                buildStep("待验证", "pending_validation", "completed", "赵工", "2023-09-09 09:00", "提交验证", "验证不通过", Collections.emptyList()),
                buildStep("修改中", "modifying", "active", "张设计", "2023-09-10 08:30", "开始修改", "",
                    buildModification("张设计", "2023-09-10 10:00", "增加了夜间照明条件说明",
                        buildTextChange("测试场景", new Map[]{
                            mapOf("type", "equal", "text", "乡村道路上，被测车辆以40km/h速度行驶，前方有行人从右侧横穿道路。"),
                            mapOf("type", "add", "text", "夜间，道路无照明设施，仅依靠车辆前照灯照明。", "author", "张设计", "time", "2023-09-10 10:00")
                        })
                    ),
                    buildModification("张设计", "2023-09-10 14:30", "调整了行人横穿速度参数范围",
                        buildTextChange("泛化要素-行人速度", new Map[]{
                            mapOf("type", "delete", "text", "[5,7]km/h", "author", "张设计", "time", "2023-09-10 14:30"),
                            mapOf("type", "add", "text", "[3,8]km/h", "author", "张设计", "time", "2023-09-10 14:30")
                        })
                    )
                ),
                buildStep("审核中", "reviewing_2", "pending", "", "", "", "", Collections.emptyList()),
                buildStep("待验证", "pending_validation_2", "pending", "", "", "", "", Collections.emptyList()),
                buildStep("已完成", "completed", "pending", "", "", "", "", Collections.emptyList())
            ));
            saveApprovalFlow("SC008", "scene", "设计中", buildFlowData(
                buildStep("设计中", "designing", "active", "李设计", "2023-09-05 09:00", "开始设计", "停车场倒车入库场景设计进行中", Collections.emptyList()),
                buildStep("审核中", "reviewing", "pending", "", "", "", "", Collections.emptyList()),
                buildStep("待验证", "pending_validation", "pending", "", "", "", "", Collections.emptyList()),
                buildStep("已完成", "completed", "pending", "", "", "", "", Collections.emptyList())
            ));

            // Case approval flows (SC001-SC004)
            saveApprovalFlow("SC001", "case", "开发中", buildFlowData(
                buildStep("开发中", "developing", "active", "赵测试", "2023-09-16 10:00", "开始开发", "用例开发进行中",
                    buildAttachment("测试用例_v1.0.docx", "赵测试", "2023-09-16 14:30", "v1.0"),
                    buildAttachment("测试数据_v1.0.xlsx", "王评价", "2023-09-17 09:15", "v1.0")
                ),
                buildStep("已完成", "completed", "pending", "", "", "", "", Collections.emptyList())
            ));
            saveApprovalFlow("SC002", "case", "开发中", buildFlowData(
                buildStep("开发中", "developing", "active", "李测试", "2023-09-17 08:30", "开始开发", "用例开发进行中",
                    buildAttachment("鬼探头测试用例_v1.0.pdf", "李测试", "2023-09-17 16:00", "v1.0")
                ),
                buildStep("已完成", "completed", "pending", "", "", "", "", Collections.emptyList())
            ));
            saveApprovalFlow("SC003", "case", "已完成", buildFlowData(
                buildStep("开发中", "developing", "completed", "张测试", "2023-09-15 09:00", "开始开发", "用例开发完成",
                    buildAttachment("隧道追尾测试用例_v1.0.docx", "张测试", "2023-09-15 14:00", "v1.0"),
                    buildAttachment("隧道追尾测试用例_v1.1.docx", "赵评价", "2023-09-16 10:30", "v1.1")
                ),
                buildStep("已完成", "completed", "completed", "张测试", "2023-09-16 11:00", "开发完成", "所有附件已上传", Collections.emptyList())
            ));
            saveApprovalFlow("SC004", "case", "开发中", buildFlowData(
                buildStep("开发中", "developing", "active", "王测试", "2023-09-14 10:00", "开始开发", "用例开发进行中",
                    buildAttachment("弯道避让测试用例_v1.0.docx", "王测试", "2023-09-14 15:00", "v1.0")
                ),
                buildStep("已完成", "completed", "pending", "", "", "", "", Collections.emptyList())
            ));
        }
    }

    private void saveApprovalFlow(String sceneId, String flowType, String currentStep, Map<String, Object> flowDataMap) {
        try {
            ApprovalFlow flow = new ApprovalFlow();
            flow.setSceneId(sceneId);
            flow.setFlowType(flowType);
            flow.setCurrentStep(currentStep);
            flow.setFlowData(objectMapper.writeValueAsString(flowDataMap));
            approvalFlowRepository.save(flow);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> buildFlowData(Map<String, Object>... steps) {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("steps", Arrays.asList(steps));
        return data;
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> buildStep(String stepName, String stepKey, String status,
                                          String operator, String operateTime, String action,
                                          String comment, Object... extraItems) {
        Map<String, Object> step = new LinkedHashMap<>();
        step.put("stepName", stepName);
        step.put("stepKey", stepKey);
        step.put("status", status);
        step.put("operator", operator);
        step.put("operateTime", operateTime);
        step.put("action", action);
        step.put("comment", comment);

        java.util.List<Object> items = new java.util.ArrayList<>();
        for (Object item : extraItems) {
            if (item instanceof java.util.List) {
                items.addAll((java.util.List<?>) item);
            } else if (item instanceof Map) {
                items.add(item);
            }
        }

        if (!items.isEmpty() && items.get(0) instanceof Map) {
            Map<String, Object> first = (Map<String, Object>) items.get(0);
            if (first.containsKey("modifier")) {
                step.put("modifications", items);
            } else if (first.containsKey("fileName")) {
                step.put("attachments", items);
            }
        }
        return step;
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> buildModification(String modifier, String modifyTime,
                                                   String modifyContent, Map<String, Object>... textChanges) {
        Map<String, Object> mod = new LinkedHashMap<>();
        mod.put("modifier", modifier);
        mod.put("modifyTime", modifyTime);
        mod.put("modifyContent", modifyContent);
        mod.put("textChanges", Arrays.asList(textChanges));
        return mod;
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> buildTextChange(String field, Map<String, Object>... segments) {
        Map<String, Object> tc = new LinkedHashMap<>();
        tc.put("field", field);
        tc.put("segments", Arrays.asList(segments));
        return tc;
    }

    private Map<String, Object> buildAttachment(String fileName, String uploader,
                                                 String uploadTime, String version) {
        Map<String, Object> att = new LinkedHashMap<>();
        att.put("fileName", fileName);
        att.put("uploader", uploader);
        att.put("uploadTime", uploadTime);
        att.put("version", version);
        return att;
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> mapOf(String... entries) {
        Map<String, Object> map = new LinkedHashMap<>();
        for (int i = 0; i < entries.length; i += 2) {
            map.put(entries[i], entries[i + 1]);
        }
        return map;
    }

    // ==================== System Settings ====================

    private void initSystemSettings() {
        if (systemSettingRepository.count() == 0) {
            try {
                java.util.List<Map<String, Object>> rolesList = new java.util.ArrayList<>();

                Map<String, Object> r1 = new LinkedHashMap<>();
                r1.put("id", "1"); r1.put("name", "管理员"); r1.put("permissions", java.util.Arrays.asList("全部"));
                rolesList.add(r1);
                Map<String, Object> r2 = new LinkedHashMap<>();
                r2.put("id", "2"); r2.put("name", "设计人员"); r2.put("permissions", java.util.Arrays.asList("场景设计", "用例设计"));
                rolesList.add(r2);
                Map<String, Object> r3 = new LinkedHashMap<>();
                r3.put("id", "3"); r3.put("name", "审核人员"); r3.put("permissions", java.util.Arrays.asList("审核", "审批"));
                rolesList.add(r3);
                Map<String, Object> r4 = new LinkedHashMap<>();
                r4.put("id", "4"); r4.put("name", "测试人员"); r4.put("permissions", java.util.Arrays.asList("用例补充", "用例转化"));
                rolesList.add(r4);
                Map<String, Object> r5 = new LinkedHashMap<>();
                r5.put("id", "5"); r5.put("name", "评价人员"); r5.put("permissions", java.util.Arrays.asList("评价指标"));
                rolesList.add(r5);

                String rolesJson = objectMapper.writeValueAsString(rolesList);
                systemSettingRepository.save(createSystemSetting("roles", rolesJson));

                Map<String, Object> exportMap = new LinkedHashMap<>();
                exportMap.put("wordExport", "true");
                exportMap.put("pdfExport", "true");
                exportMap.put("excelExport", "true");
                String exportJson = objectMapper.writeValueAsString(exportMap);
                systemSettingRepository.save(createSystemSetting("exportSettings", exportJson));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private SystemSetting createSystemSetting(String key, String value) {
        SystemSetting setting = new SystemSetting();
        setting.setSettingKey(key);
        setting.setSettingValue(value);
        return setting;
    }
}