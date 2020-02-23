<template>
    <div>
        <div  v-for="curSource in data">
        <el-collapse >
            <el-collapse-item :title="curSource.name">
                <p>Количество файлов: {{curSource.fileCount}}</p>
                <p>Описание: {{curSource.description}}</p>
                <p>Направление: {{curSource.direction}}</p>
                <p>Ответственный за ведение: {{curSource.management}}</p>
                <p>Дата создания: {{curSource.dateCreation}}</p>
                <el-collapse>
                    <el-collapse-item title="шаблон">

                    </el-collapse-item>
                </el-collapse>
            </el-collapse-item>
        </el-collapse>
        </div>
    </div>
</template>

<script>
    export default {
        name: "ShowSource",
        props:['data'],
        methods:{

        }
    }
</script>

<style scoped>

</style>