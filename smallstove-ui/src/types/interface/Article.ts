import Type from '@/types/interface/Type'

export default interface Article {
    articleId: number
    userId: number
    nickName: string
    avatar: string
    title: string
    thumbnail: string
    content: string
    typeVoList?: Array<Type>
    viewsNumber: number
    likesNumber: number
    allowComment: boolean
    status: boolean
    createTime: Date
}
