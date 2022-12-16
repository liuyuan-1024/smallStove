/**
 * 响应是否成功
 * @param code 响应码
 */
export function isSuccess(code: number) {
    return String(code).substring(0, 3) === '200';
}
