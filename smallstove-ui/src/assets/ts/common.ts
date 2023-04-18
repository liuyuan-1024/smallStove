// noinspection JSIgnoredPromiseFromCall

import router from '@/router'
import { useRoute } from 'vue-router'

export function back() {
  router.push('/')
}

export function toMain() {
  router.push('/')
}

/**
 * 查看本人关注的用户发表的相关帖子
 * @param userId 本人ID
 */
export function toAttention(userId: number) {
  router.push({ name: 'Attention', params: { userId: userId } })
}

export function toPublishArticle() {
  router.push('/publishArticle')
}

export function toLogin() {
  router.push('/login')
}

export function toNotification() {
  router.push('/notification')
}

export function toChat() {
  router.push('/chat')
}

export function toFavorites() {
  router.push('/favorites')
}

export function toMy(userId: number) {
  console.log('我的', userId)
  router.push({ name: 'My', params: { userId: userId } })
}

export function toArticleDetails(articleId: any) {
  router.push({ name: 'ArticleDetails', params: { articleId: articleId } })
}

export function toTagDetails() {
  router.push('/tagDetails')
}

// 判断当前是否已登录 todo 如果有人用已过期的token放入localStorage,咋办？
export function isLogin() {
  const access = localStorage.getItem('access_token')
  return !(access === null || access === undefined || access === '')
}

// 判断当前页面是否为文章列表页
export function isArticleListView() {
  return useRoute().path === '/'
}

// 判断当前是否在发帖页面
export function isPublish() {
  return useRoute().path === '/publishArticle'
}
