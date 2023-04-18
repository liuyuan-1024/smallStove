import requests from '@/api/requests'
import { ElMessage } from 'element-plus/es'
import { isSuccess } from '@/utils/common'

/**
 * 分页获取文章列表
 * @param args 分页数据: 当前页, 页尺寸, 类型ID, 用户ID
 */
export async function getArticleList(args: {
  currentPage: number
  pageSize: number
  typeId?: number
  userId?: number
}) {
  return requests.get('/anonymous/article-list', { params: args })
}

/**
 * 获取文章详情   异步方法即使使用了await, 返回值也是promise对象
 * @param articleId 文章ID
 */
export async function getArticleDetails(articleId: unknown) {
  return requests.get(`/anonymous/article-details/${articleId}`)
}

/**
 * 点赞
 * @param articleId
 */
export async function likes(articleId: unknown) {
  requests.put(`/likes/${articleId}`).then((value: any) => {
    if (isSuccess(value.code)) {
      ElMessage.success(value.message)
    }
  })
}

/**
 * 查询文章标签
 */
export async function getTags() {
  return requests.get('/tags')
}

/**
 * 图片上传
 */
export async function upload() {
  return requests.post('/upload-image')
}
