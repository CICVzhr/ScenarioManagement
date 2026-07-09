import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    name: 'Dashboard',
    meta: { title: '总览' },
    component: () => import('@/views/Dashboard.vue')
  },
  {
    path: '/accident-data',
    name: 'AccidentData',
    meta: { title: '场景数据' },
    component: () => import('@/views/AccidentData.vue')
  },
  {
    path: '/scenario-design',
    name: 'ScenarioDesign',
    meta: { title: '场景设计' },
    component: () => import('@/views/ScenarioDesign.vue')
  },
  {
    path: '/danger-library',
    name: 'DangerLibrary',
    meta: { title: '危险场景库' },
    component: () => import('@/views/DangerLibrary.vue')
  },
  {
    path: '/case-design',
    name: 'CaseDesign',
    meta: { title: '用例设计' },
    component: () => import('@/views-ex/CaseDesign.vue')
  },
  {
    path: '/test-case',
    name: 'TestCase',
    meta: { title: '测试用例库' },
    component: () => import('@/views-ex/TestCase.vue')
  },
  {
    path: '/test-management',
    name: 'TestManagement',
    meta: { title: '测试管理' },
    component: () => import('@/views-ex/TestManagement.vue')
  },
  {
    path: '/evaluation',
    name: 'Evaluation',
    meta: { title: '评价管理' },
    component: () => import('@/views-ex/Evaluation.vue')
  },
  {
    path: '/statistics',
    name: 'Statistics',
    meta: { title: '统计分析' },
    component: () => import('@/views-ex/Statistics.vue')
  },
  {
    path: '/tag-management',
    name: 'TagManagement',
    meta: { title: '标签管理' },
    component: () => import('@/views/TagManagement.vue')
  },
  {
    path: '/system-settings',
    name: 'SystemSettings',
    meta: { title: '系统设置' },
    component: () => import('@/views/SystemSettings.vue')
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router