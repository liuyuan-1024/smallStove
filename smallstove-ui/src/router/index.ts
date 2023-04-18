import { createRouter, createWebHashHistory } from 'vue-router'
import { ElMessage } from 'element-plus'

import ArticleListView from '@/views/article/ArticleList.vue'
import ArticleDetailsView from '@/views/article/ArticleDetails.vue'
import TagDetailsView from '@/views/TagDetailsView.vue'
import LoginView from '@/views/LoginView.vue'
import AttentionView from '@/views/AttentionView.vue'
import PublishArticleView from '@/views/article/PublishArticle.vue'
import ChatView from '@/views/ChatView.vue'
import NotificationView from '@/views/NotificationView.vue'
import FavoritesView from '@/views/FavoritesView.vue'
import MyView from '@/views/MyView.vue'
import { isSuccess } from '@/utils/common'
import { checkToken } from '@/api/user'
import { useLoginUserStore } from '@/stores'
import { delAccessTokenFromLocal, delRefreshTokenFromLocal, getAccessTokenFromLocal } from '@/utils/storage'

const routes = [
  {
    // 文章列表(主页 推荐)
    path: '/',
    name: 'Main',
    component: ArticleListView,
    meta: { auth: false, keepAlive: true, title: '开源小灶' }
  },
  {
    // 文章详情
    path: '/articleDetails/:articleId',
    name: 'ArticleDetails',
    component: ArticleDetailsView,
    meta: { auth: false, keepAlive: false }
  },
  {
    // 标签详情
    path: '/tagDetails',
    name: 'TagDetails',
    component: TagDetailsView,
    meta: { auth: false, keepAlive: false }
  },
  {
    // 登录
    path: '/login',
    name: 'Login',
    component: LoginView,
    meta: { auth: false, keepAlive: false, title: '登录-开源小灶' }
  },
  {
    // 关注
    path: '/attention/:userId',
    name: 'Attention',
    component: AttentionView,
    meta: { auth: true, keepAlive: false, title: '关注-开源小灶' }
  },
  {
    // 发帖
    path: '/publishArticle',
    name: 'PublishArticle',
    component: PublishArticleView,
    meta: { auth: true, keepAlive: false, title: '发帖-开源小灶' }
  },
  {
    // 通知
    path: '/notification',
    name: 'Notification',
    component: NotificationView,
    meta: { auth: true, keepAlive: false, title: '通知-开源小灶' }
  },
  {
    // 私信
    path: '/chat',
    name: 'Chat',
    component: ChatView,
    meta: { auth: true, keepAlive: false, title: '私信-开源小灶' }
  },
  {
    // 收藏
    path: '/favorites',
    name: 'Favorites',
    component: FavoritesView,
    meta: { auth: true, keepAlive: false, title: '收藏-开源小灶' }
  },
  {
    // 我的
    path: '/my/:userId',
    name: 'My',
    component: MyView,
    meta: { auth: true, keepAlive: false, title: '我的-开源小灶' }
  }
]

// router对象
const router = createRouter({
  history: createWebHashHistory(),
  routes,
  scrollBehavior() {
    // return 期望滚动到哪个的位置, false原位置
    return false
  }
})

// 全局前置路由守卫
// router对象在跳转页面前 to:去哪里 from:来自哪里 next:函数, 用于放行路由, 也能入参,可以改变放行路径
router.beforeEach((to, from, next) => {
  const loginUserStore = useLoginUserStore()

  // 判断to是否需要权限
  if (to.meta.auth) {
    const access_token = getAccessTokenFromLocal()
    // 用户未登录, 提示用户需要登录
    if (access_token === null || access_token === undefined || access_token === '') {
      ElMessage.warning('未登录, 请登录后操作!')
      next('/login')
      return
    }
    // 用户已登录, 发送请求判断access_token是否过期
    checkToken().then((res: any) => {
      if (isSuccess(res.code)) {
        loginUserStore.reset()
        next()
      } else {
        // 身份已过期, 响应拦截器会弹窗, 这里不需要弹窗
        delAccessTokenFromLocal()
        delRefreshTokenFromLocal()
        next('/login')
      }
    })
  } else {
    loginUserStore.reset()
    next()
  }
})

// 全局后置路由守卫
router.afterEach((to) => {
  window.document.title = String(to.meta.title)
})
export default router
