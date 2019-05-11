# robot.message

这个包仅在测试中被 `compile`  
其余的均使用 `compileOnly`  
这意味着需要在目标平台上实现这个包的内容  

包根路径: `org.hoshino9.robot.message`
类:
* RawStringMessage(val raw: String) : Message           原生字符串消息
* JsonMessage(val json: String) : Message               `Json` 格式消息
* XmlMessage(val xml: String) : Message                 `Xml` 格式消息
* component.AtMessage(val target: Member) : Message     `at` 消息**组件**

特别的:
* component.ComponentsMessage() : RawStringMessage("")  可以被直接应用在代码中而不需要适应目标平台

注意的:
* ConsoleMessage() : Message    此层不应该出现在真正实现中，仅供测试使用