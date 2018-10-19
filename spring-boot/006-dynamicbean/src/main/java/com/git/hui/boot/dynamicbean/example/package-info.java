/**
 * 用来演示动态注册Bean的demo项目
 *
 * 场景说明：
 *  - 假设我们依赖第三方的服务，UserService 获取用户信息
 *  - 我在本地启动时，也不想直接去调用真正的第三方服务，只需要在本地mock一个返回即可，然后验证我自己的后续业务逻辑是否有问题
 *
 * 因此，主要就会涉及到
 *  - 删除已经注册到Spring容器的UserService
 *  - 动态创建一个Mock的服务，注册到Spring容器
 *
 * Created by @author yihui in 17:01 18/10/16.
 */
package com.git.hui.boot.dynamicbean.example;