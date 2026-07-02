package com.example.scenario.config;

import com.example.scenario.entity.*;
import com.example.scenario.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

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
    }

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
                    "云南省丽江市山路", "山区道路", "小雨", "理想L9", "NOA 3.0", "事故", "重大", "已驳回")
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

    private void initSceneDesign() {
        if (sceneDesignRepository.count() == 0) {
            sceneDesignRepository.saveAll(Arrays.asList(
                createSceneDesign("SC001", "雨天高速公路多车碰撞场景", "刘设计",
                    "雨天,高速公路,多车碰撞", "王工,李工", "待修改", 0, "2023-09-12"),
                createSceneDesign("SC002", "城市路口非机动车鬼探头场景", "陈设计",
                    "城市道路,交叉口,非机动车", "王工,李工", "已修改 2 处", 2, "2023-09-10"),
                createSceneDesign("SC003", "夜间隧道追尾场景", "张设计",
                    "夜间,隧道,追尾", "王工,赵工", "设计中", 0, "2023-09-08"),
                createSceneDesign("SC004", "山区道路弯道障碍物避让场景", "李设计",
                    "山区道路,弯道,障碍物", "王工,孙工", "待审核", 0, "2023-09-06")
            ));
        }
    }

    private SceneDesign createSceneDesign(String id, String name, String designer, String tags,
                                          String verifiers, String status, Integer modifyCount, String createTime) {
        SceneDesign design = new SceneDesign();
        design.setId(id);
        design.setName(name);
        design.setDesigner(designer);
        design.setTags(tags);
        design.setVerifiers(verifiers);
        design.setStatus(status);
        design.setModifyCount(modifyCount);
        design.setCreateTime(LocalDate.parse(createTime));
        return design;
    }

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

    private void initProcessTasks() {
        if (processTaskRepository.count() == 0) {
            processTaskRepository.saveAll(Arrays.asList(
                createProcessTask("雨天高速公路多车碰撞场景", "数据筛选审核", "张晓明", "2023-09-15T14:30:00", "待审核"),
                createProcessTask("雨天高速公路非机动车鬼探头场景", "数据筛选审核", "张晓明", "2023-09-14T10:15:00", "待审核"),
                createProcessTask("城市路口非机动车鬼探头场景", "场景设计", "李思琪", "2023-09-14T10:15:00", "设计中"),
                createProcessTask("山区道路弯道障碍物避让场景", "场景验证", "王建国", "2023-09-13T16:45:00", "待验证"),
                createProcessTask("高速追尾事故场景分析", "用例设计", "赵海洋", "2023-09-12T09:20:00", "已完成")
            ));
        }
    }

    private ProcessTask createProcessTask(String name, String stage, String submitter,
                                          String submitTime, String status) {
        ProcessTask task = new ProcessTask();
        task.setName(name);
        task.setStage(stage);
        task.setSubmitter(submitter);
        task.setSubmitTime(LocalDateTime.parse(submitTime));
        task.setStatus(status);
        return task;
    }

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
}