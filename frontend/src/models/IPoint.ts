
export interface IPoint {
    x: number
    y: number
    r: number
}

export interface IPointFetched extends IPoint {
    id: number
    result: boolean
    createTime: string
}