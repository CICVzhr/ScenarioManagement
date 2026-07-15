import { ref, computed } from 'vue'

const token = ref(localStorage.getItem('token') || '')
const userInfo = ref(JSON.parse(localStorage.getItem('userInfo') || 'null'))
const permissions = ref(JSON.parse(localStorage.getItem('permissions') || '[]'))

export function useUserStore() {
  const isLoggedIn = computed(() => !!token.value)

  const setLoginInfo = (data) => {
    token.value = data.token
    userInfo.value = {
      userId: data.userId,
      username: data.username,
      realName: data.realName,
      roles: data.roles
    }
    permissions.value = data.permissions || []
    localStorage.setItem('token', data.token)
    localStorage.setItem('userInfo', JSON.stringify(userInfo.value))
    localStorage.setItem('permissions', JSON.stringify(permissions.value))
  }

  const logout = () => {
    token.value = ''
    userInfo.value = null
    permissions.value = []
    localStorage.removeItem('token')
    localStorage.removeItem('userInfo')
    localStorage.removeItem('permissions')
  }

  const hasPermission = (permCode) => {
    if (!permCode) return true
    return permissions.value.includes(permCode)
  }

  const hasAnyPermission = (permCodes) => {
    if (!permCodes || permCodes.length === 0) return true
    return permCodes.some(code => permissions.value.includes(code))
  }

  return {
    token,
    userInfo,
    permissions,
    isLoggedIn,
    setLoginInfo,
    logout,
    hasPermission,
    hasAnyPermission
  }
}