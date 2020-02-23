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
                        <collapse-show :data="table"/>
            </el-collapse-item>
        </el-collapse>
        </div>
    </div>
</template>

<script>
    import CollapseShow from "./CollapseShow.vue";
    export default {
        name: "ShowSource",
        components: {CollapseShow},
        props:['data','table'],
        methods:{

        }
    }
</script>

<style scoped>

</style>