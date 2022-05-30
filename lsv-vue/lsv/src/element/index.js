import { Autocomplete, Button, Tag, Popover, Upload } from 'element-ui'
const element = {
  install: function (Vue) {
    Vue.use(Autocomplete)
    Vue.use(Button)
    Vue.use(Tag)
    Vue.use(Popover)
    Vue.use(Upload)
  }
}
export default element
