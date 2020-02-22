<template>
    <div>
        <div class="sub-title">Введите название источника</div>
        <el-form-item>
            <el-input placeholder="Введите название источника" v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item>
            <el-button  type="primary" @click="onSubmit">Создать</el-button>
        </el-form-item>
    </div>
</template>

<script>
    import {AXIOS} from "../AXIOS/http-common";

    export default {
        name: "CreateSource",
        data() {
            return {
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