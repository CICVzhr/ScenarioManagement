import { get, post, del } from './index'

export const getTags = () => get('/tags')
export const getTagsGrouped = () => get('/tags/grouped')
export const createTag = (data) => post('/tags', data)
export const deleteTag = (id) => del(`/tags/${id}`)
