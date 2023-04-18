export default interface Result<T> {
  code: number
  message: string
  data: T
}
