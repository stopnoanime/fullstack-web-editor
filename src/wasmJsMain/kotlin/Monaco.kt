@file:JsModule("monaco-editor")
@file:JsQualifier("editor")

import org.w3c.dom.Element

external interface IStandaloneCodeEditor {
    fun getValue(): String
    fun setValue(s: String)

}
@JsName("create")
external fun createMonacoEditor(
    domElement: Element,
): IStandaloneCodeEditor
