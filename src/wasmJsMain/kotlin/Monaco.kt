@file:JsModule("monaco-editor")
@file:JsQualifier("editor")

import org.w3c.dom.Element

external interface IStandaloneCodeEditor {
    fun getValue(): String
    fun setValue(value: String)
}

@JsName("setTheme")

external fun setMonacoTheme(
    themeName: String,
)

@JsName("create")
external fun createMonacoEditor(
    domElement: Element,
): IStandaloneCodeEditor

