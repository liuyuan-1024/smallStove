import Token from "@/types/interface/Token";

export default interface LoginUser {
    id: number;
    username: string;
    nickName: string;
    email: string;
    mobile: string;
    sex: number;
    avatar: string;
    token: Token;
}