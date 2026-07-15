import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '@/stores/userStore'
import { ElMessage } from 'element-plus'

const routes = [
  {
    path: '/login',
    name: 'Login',
    meta: { title: '登录', noAuth: true },
    component: () => import('@/views/Login.vue')
  },
  {
    path: '/',
    name: 'Dashboard',
    meta: { title: '总览' },
    component: () => import('@/views/Dashboard.vue')
  },
  {
    path: '/accident-data',
    name: 'AccidentData',
    meta: { title: '场景数据', perm: 'accident_data' },
    component: () => import('@/views/AccidentData.vue')
  },
  {
    path: '/scenario-design',
    name: 'ScenarioDesign',
    meta: { title: '场景设计', perm: 'scenario_design' },
    component: () => import('@/views/ScenarioDesign.vue')
  },
  {
    path: '/danger-library',
    name: 'DangerLibrary',
    meta: { title: '危险场景库', perm: 'danger_library' },
    component: () => import('@/views/DangerLibrary.vue')
  },
  {
    path: '/case-design',
    name: 'CaseDesign',
    meta: { title: '用例设计', perm: 'case_design' },
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
    meta: { title: '标签管理', perm: 'tag_management' },
    component: () => import('@/views/TagManagement.vue')
  },
  {
    path: '/system-settings',
    name: 'SystemSettings',
    meta: { title: '系统设置', perm: 'system_settings' },
    component: () => import('@/views/SystemSettings.vue')
  },
  {
    path: '/user-management',
    name: 'UserManagement',
    meta: { title: '用户管理', perm: 'user_management' },
    component: () => import('@/views/UserManagement.vue')
  },
  {
    path: '/role-management',
    name: 'RoleManagement',
    meta: { title: '角色管理', perm: 'user_management' },
    component: () => import('@/views/RoleManagement.vue')
  },
  {
    path: '/permission-management',
    name: 'PermissionManagement',
    meta: { title: '权限管理', perm: 'user_management' },
    component: () => import('@/views/PermissionManagement.vue')
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  const userStore = useUserStore()
  if (to.meta.noAuth) {
    next()
    return
  }
  if (!userStore.isLoggedIn.value) {
    next('/login')
    return
  }
  if (to.meta.perm && !userStore.hasPermission(to.meta.perm)) {
    ElMessage ? ElMessage.warning('无访问权限') : console.warn('无访问权限')
    next('/')
    return
  }
  next()
})

export default router