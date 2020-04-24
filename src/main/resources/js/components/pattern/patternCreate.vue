<template>
    <div>
        <el-row :gutter="20">
            <el-col :span="12">
                <div>
                    <el-form :model="pattern" :rules="rules" ref="pattern" :label-position="labelPosition" label-width="100px">
                        <el-form-item prop="name" label="Название">
                            <el-input @input="hiddenAddPatternBtn" v-model="pattern.name"></el-input>
                        </el-form-item>
                        <el-form-item prop="description" label="Описание">
                            <el-input @input="hiddenAddPatternBtn" v-model="pattern.description"></el-input>
                        </el-form-item>
                    </el-form>
                </div>
            </el-col>
            <el-col :span="12">
                <div>
                    <el-form :model="pattern" :rules="rules" ref="pattern" :label-position="labelPosition" label-width="100px">
                        <el-form-item prop="direction" label="Направление:">
                            <el-input @input="hiddenAddPatternBtn" v-model="pattern.direction"></el-input>
                        </el-form-item>
                        <el-form-item prop="management" label="Отвтественный за ведение:">
                            <el-input @input="hiddenAddPatternBtn" v-model="pattern.management"></el-input>
                        </el-form-item>
                    </el-form>
                </div>
            </el-col>
        </el-row>
        <el-button v-if="patternAddBtn" @click="createPattern" style="background-color: #1ab394; border-color: #1ab394; color: white;">Добавить шаблон</el-button>

    </div>
</template>

<script>
    import {AXIOS} from "../../AXIOS/http-common";

    export default {
        name: "patternCreate",
        props: ['sourceId'],
        data(){
            return{
                patternAddBtn:false,
                activeName: "sourceInfo",
                labelPosition:"top",
                pattern: {
                    name: "",
                    description: "",
                    direction: "",
                    management: "",
                    sourceId: "",
                },
                rules: {
                    name: [
                        { required: true, message: 'Заполните поле', trigger: 'blur' }
                    ],
                    longName: [
                        { required: true, message: 'Заполните поле', trigger: 'blur' }
                    ],
                    shortName: [
                        { required: true, message: 'Заполните поле', trigger: 'blur' }
                    ],
                    description: [
                        { required: true, message: 'Заполните поле', trigger: 'blur' }
                    ],
                    addDescription: [
                        { required: true, message: 'Заполните поле', trigger: 'blur' }
                    ],
                    scope: [
                        { required: true, message: 'Заполните поле', trigger: 'blur' }
                    ],
                    periodicity: [
                        { required: true, message: 'Заполните поле', trigger: 'blur' }
                    ],
                    renewalPeriod: [
                        { required: true, message: 'Заполните поле', trigger: 'blur' }
                    ],
                    type: [
                        { required: true, message: 'Заполните поле', trigger: 'blur' }
                    ],
                    tags: [
                        { required: true, message: 'Заполните поле', trigger: 'blur' }
                    ],
                    providerLink: [
                        { required: true, message: 'Заполните поле', trigger: 'blur' }
                    ],
                    dataSource: [
                        { required: true, message: 'Заполните поле', trigger: 'blur' }
                    ],
                    patternName: [
                        { required: true, message: 'Заполните поле', trigger: 'blur' }
                    ],
                    patternDescription: [
                        { required: true, message: 'Заполните поле', trigger: 'blur' }
                    ],
                    direction: [
                        { required: true, message: 'Заполните поле', trigger: 'blur' }
                    ],
                    management: [
                        { required: true, message: 'Заполните поле', trigger: 'blur' }
                    ],
                }
            }
        },
        methods:{
            hiddenAddPatternBtn(){
                this.patternAddBtn = this.pattern.name !== "" &&
                    this.pattern.description !== "" &&
                    this.pattern.direction !== "" &&
                    this.pattern.management !== "";
            },

            createPattern() {
                let formData = new FormData();
                formData.append("name",this.pattern.name);
                formData.append("description",this.pattern.description);
                formData.append("direction",this.pattern.direction);
                formData.append("management",this.pattern.management);
                formData.append("sourceId",this.sourceId);
                AXIOS.post("/pattern/create",
                    formData,
                    {
                        headers: {
                            'Content-Type': 'multipart/form-data'
                        }
                    }
                ).then(response => {
                    if(response.data.name == null){
                        this.noticeError('Ошибка при создании шаблона.');
                    } else {
                        this.noticeSuccess('Шаблон"' + response.data.name + '" успешно создан.');
                    }
                });
            },

            noticeError(message) {
                this.$notify({
                    title: 'Ошибка',
                    message:message ,
                    type: 'error',
                });
            },

            noticeSuccess(message) {
                this.$notify({
                    title: 'Успешно',
                    dangerouslyUseHTMLString: true,
                    message: message,
                    type: 'success',
                });
            },
        }
    }
</script>

<style scoped>

</style>