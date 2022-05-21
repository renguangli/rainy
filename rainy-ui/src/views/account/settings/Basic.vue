<template>
  <div class="account-settings-info-view">
    <a-row :gutter="16" type="flex" justify="center">
      <a-col :order="isMobile ? 2 : 1" :md="24" :lg="16">
        <a-form class="form" layout="vertical">
          <a-form-item
            :label="$t('account.settings.basic.nickname')"
          >
            <a-input :placeholder="$t('account.settings.basic.nickname-message')" v-model="nickname" />
          </a-form-item>
          <a-form-item
            label="标签"
          >
            <div>
              <template v-for="(tag, index) in tags">
                <a-tooltip v-if="tag.length > 20" :key="tag" :title="tag">
                  <a-tag
                    class="tagsTitle"
                    :key="tag"
                    :closable="index !== 0"
                    :close="() => handleTagClose(tag)"
                  >{{ `${tag.slice(0, 20)}...` }}</a-tag>
                </a-tooltip>
                <a-tag
                  class="tagsTitle"
                  v-else
                  :key="tag"
                  :closable="index !== 0"
                  :close="() => handleTagClose(tag)"
                >{{ tag }}</a-tag>
              </template>
              <a-input
                v-if="tagInputVisible"
                ref="tagInput"
                type="text"
                size="small"
                :style="{ width: '78px' }"
                :value="tagInputValue"
                placeholder="enter"
                @change="handleInputChange"
                @blur="handleTagInputConfirm"
                @keyup.enter="handleTagInputConfirm"
              />
              <a-tag class="tagsTitle" v-else @click="showTagInput" style="background: #fff; borderStyle: dashed;">
                <a-icon type="plus"/>New Tag
              </a-tag>
            </div>
          </a-form-item>
          <a-form-item
            :label="$t('account.settings.basic.profile')"
          >
            <a-textarea rows="4" :placeholder="$t('account.settings.basic.profile-message')" value="做个人吧！"/>
          </a-form-item>

          <a-form-item
            :label="$t('account.settings.basic.email')"
            :required="false"
          >
            <a-input placeholder="请输入邮箱" v-model="userInfo.email"/>
          </a-form-item>
          <a-form-item
            :label="$t('account.settings.basic.telephone')"
            :required="false"
          >
            <a-input placeholder="请输入手机号" v-model="userInfo.telephone"/>
          </a-form-item>
          <a-form-item>
            <a-button type="primary">{{ $t('account.settings.basic.update') }}</a-button>
          </a-form-item>
        </a-form>
      </a-col>
      <a-col :order="1" :md="24" :lg="8" :style="{ minHeight: '180px' }">
        <div class="ant-upload-preview" @click="$refs.modal.edit(1)" >
          <a-icon type="cloud-upload-o" class="upload-icon"/>
          <div class="mask">
            <a-icon type="plus" />
          </div>
          <img :src="avatar"/>
        </div>
      </a-col>
    </a-row>
    <avatar-modal ref="modal" @ok="setAvatar"/>
  </div>
</template>

<script>
import AvatarModal from './AvatarModal'
import { baseMixin } from '@/store/app-mixin'
import { mapGetters } from 'vuex'

export default {
  mixins: [baseMixin],
  components: {
    AvatarModal
  },
  data () {
    return {
      // cropper
      tags: ['辣~', '大长腿', '川妹子', '海纳百川'],
      tagInputVisible: false,
      tagInputValue: '',
      preview: {},
      option: {
        info: true,
        size: 1,
        outputType: 'jpeg',
        canScale: false,
        autoCrop: true,
        // 只有自动截图开启 宽度高度才生效
        autoCropWidth: 180,
        autoCropHeight: 180,
        fixedBox: true,
        // 开启宽度和高度比例
        fixed: true,
        fixedNumber: [1, 1]
      }
    }
  },
  computed: {
    ...mapGetters(['nickname', 'avatar', 'userInfo'])
  },
  methods: {
    setAvatar (url) {
      this.$store.commit('SET_AVATAR', process.env.VUE_APP_API_BASE_URL + url)
      // this.option.img = process.env.VUE_APP_API_BASE_URL + url
    },
    handleTagClose (removeTag) {
      this.tags = this.tags.filter(tag => tag !== removeTag)
    },
    showTagInput () {
      this.tagInputVisible = true
      this.$nextTick(() => {
        this.$refs.tagInput.focus()
      })
    },
    handleInputChange (e) {
      this.tagInputValue = e.target.value
    },
    handleTagInputConfirm () {
      const inputValue = this.tagInputValue
      let tags = this.tags
      if (inputValue && !tags.includes(inputValue)) {
        tags = [...tags, inputValue]
      }

      Object.assign(this, {
        tags,
        tagInputVisible: false,
        tagInputValue: ''
      })
    }
  }
}
</script>

<style lang="less" scoped>

  .avatar-upload-wrapper {
    height: 200px;
    width: 100%;
  }

  .ant-upload-preview {
    position: relative;
    margin: 0 auto;
    width: 100%;
    max-width: 180px;
    border-radius: 50%;
    box-shadow: 0 0 4px #ccc;

    .upload-icon {
      position: absolute;
      top: 0;
      right: 10px;
      font-size: 1.4rem;
      padding: 0.5rem;
      background: rgba(222, 221, 221, 0.7);
      border-radius: 50%;
      border: 1px solid rgba(0, 0, 0, 0.2);
    }
    .mask {
      opacity: 0;
      position: absolute;
      background: rgba(0,0,0,0.4);
      cursor: pointer;
      transition: opacity 0.4s;

      &:hover {
        opacity: 1;
      }

      i {
        font-size: 2rem;
        position: absolute;
        top: 50%;
        left: 50%;
        margin-left: -1rem;
        margin-top: -1rem;
        color: #d6d6d6;
      }
    }

    img, .mask {
      width: 100%;
      max-width: 180px;
      height: 100%;
      border-radius: 50%;
      overflow: hidden;
    }
  }

  .ant-form-item {
    margin-bottom: 10px;
  }
  .tagsTitle {
    font-weight: 500;
    margin-bottom: 12px;
  }
</style>
