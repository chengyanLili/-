/*
* 正则表达式 验证
* */

/*
* 是否为手机号
* */
export function validate_isMobilePhoneNumber(value) {
    return /^[1][3,4,5,6,7,8,9][0-9]{9}$/.test(value)
  }
  /*
  * 是否为 正 整数 不包含 0
  * */
  export function validate_isInteger(value) {
    return /^[1-9]\d*$/.test(value)
  }
  /*
  * 是否为 身份证号
  * */
  export function validate_isIDCard(value) {
    return /^[1-9]\d{5}(18|19|20)\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/.test(value)
  }
    /*
    *  特殊字符校验
    * */
  export function checkSpecicalKey(str) {
      // 以下特殊字符禁用
      let specialKey =
          "[`~!@#$^&*¥-+=/=|{}';'\\[||]<>/?·~！@#￥%&……*————{}——|《》‘’【】''、？]";
      for (let i = 0; i < str.length; i++) {
          if (specialKey.indexOf(str.substr(i, 1)) != -1) {
              return false;
          }
      }
      return true;
  }