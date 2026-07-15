import { useUserStore } from '@/stores/userStore'

export default {
  mounted(el, binding) {
    const userStore = useUserStore()
    const permissionCode = binding.value
    if (permissionCode && !userStore.hasPermission(permissionCode)) {
      el.style.display = 'none'
    }
  },
  updated(el, binding) {
    const userStore = useUserStore()
    const permissionCode = binding.value
    if (permissionCode && !userStore.hasPermission(permissionCode)) {
      el.style.display = 'none'
    } else {
      el.style.display = ''
    }
  }
}