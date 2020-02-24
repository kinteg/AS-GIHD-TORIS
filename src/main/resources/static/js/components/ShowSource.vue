<template>
    <div>
        <el-collapse >
            <el-collapse-item :title="pattern.name">
                <p>Количество файлов: {{pattern.fileCount}}</p>
                <p>Описание: {{pattern.description}}</p>
                <p>Направление: {{pattern.direction}}</p>
                <p>Ответственный за ведение: {{pattern.management}}</p>
                <p>Дата создания: {{pattern.dateCreation}}</p>
                <el-collapse >
                    <el-collapse-item title="Шаблоны">
                        <collapse-show :data="table"/>
                    </el-collapse-item>
                </el-collapse>
            </el-collapse-item>
        </el-collapse>
    </div>
</template>

<script>
    import CollapseShow from "./CollapseShow.vue";
    export default {
        name: "ShowSource",
        components: {CollapseShow},
        props:['pattern','table'],
        methods:{

        }
    }
</script>

<style scoped>

</style>