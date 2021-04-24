package cn.zjw.flowable.flowable;

import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;

/**
 * FlowTest
 *
 * @author zjw
 * @date 2021/3/19
 */
public class FlowTest {

    public static void main(String[] args) {
        ProcessEngineConfiguration cfg = new StandaloneProcessEngineConfiguration()
                .setJdbcUrl("jdbc:mysql://localhost:3306/flow_study?serverTimezone=GMT%2B8&charset=utf8mb4&useSSL=false&nullCatalogMeansCurrent=true")
                .setJdbcUsername("root")
                .setJdbcPassword("root")
                .setJdbcDriver("com.mysql.cj.jdbc.Driver");
        ProcessEngine processEngine = cfg.buildProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("holiday-request.bpmn20.xml")
                .deploy();
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .deploymentId(deployment.getId())
                .singleResult();
        System.out.println("Found process definition : " + processDefinition.getName());
    }

}
