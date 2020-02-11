<template>
    <div>
        <el-form ref="form" :model="form" label-width="120px">
            <el-form-item label="Activity name">
                <el-input v-model="form.name"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="onSubmit">Create</el-button>
                <el-button>Cancel</el-button>
            </el-form-item>
        </el-form>
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
                AXIOS.post( '/create',
                    {name: this.form.name}
                )
            }
        }
    }
</script>