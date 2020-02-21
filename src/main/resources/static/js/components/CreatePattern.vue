<template>
    <el-form :label-position="labelPosition" label-width="100px" :model="formLabelAlign">
        <el-form-item label="Описание">
            <el-input v-model="formLabelAlign.name"></el-input>
        </el-form-item>
        <el-form-item label="Направление">
            <el-input v-model="formLabelAlign.region"></el-input>
        </el-form-item>
        <el-form-item label="Ответственный за ведение">
            <el-input v-model="formLabelAlign.type"></el-input>
        </el-form-item>
    </el-form>

    <upload-file :controller=""/>
</template>

<script>
    import UploadFile from "./UploadFile.vue";
    export default {
        name: "CreatePattern",
        components: {UploadFile},
        data() {
            return {
                labelPosition: 'left',
                formLabelAlign: {
                    description: '',
                    direction: '',
                    management: ''
                }
            };
        }
    }
</script>

<style scoped>

</style>