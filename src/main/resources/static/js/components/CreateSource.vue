<template>
    <div>
        <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="120px" class="demo-ruleForm">
            <el-form-item label="Имя источника" prop="text">
                <el-input placeholder="Введите название источника" v-model="ruleForm.text"></el-input>
                <el-button style="margin-top: 10px" type="primary" @click="onSubmit">Создать</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script>
    import {AXIOS} from "../AXIOS/http-common";

    export default {
        name: "CreateSource",
        data() {
            let symbol = new RegExp( "[~!@#$%^&*()\\-+=|\/';:,.]");
            let validatePass = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('Заполните поле'));
                } else {
                    if(symbol.exec(value)!==null){
                        callback(new Error('Недопустимые символы: ~!@#$%^&*()-+=|  / \';:,.'));
                    }else
                        callback();
                }
            };

            return {
                ruleForm: {
                    text: '',
                },
                rules: {
                    text: [
                        { validator: validatePass, trigger: 'blur' }
                    ],
                },

                form: {
                    name: '',
                }
            }
        },
        methods: {
            onSubmit() {
                AXIOS.post( '/source/create',
                    {name: this.form.name}
                )
            }
        }
    }
</script>