export class UserInfo {
    age: number
    name: string
    poem: Poem
    remainingDays: number
}

export class Poem {
    content: string
    poet: { name: string, url: string }
    title: string
    url: string
}