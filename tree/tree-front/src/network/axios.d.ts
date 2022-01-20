import {AxiosStatic} from "axios";

declare module 'axios' {
    interface IAxios extends AxiosStatic {
        code: string
        message: string
    }

    export interface AxiosResponse<T = any> extends IAxios {}
}

