declare module 'axios' {
    interface IAxios<D = null> {
        code: string
        message: string
        extra: D
    }
    // @ts-ignore
    export interface AxiosResponse<T = any> extends IAxios<D> {}
}

