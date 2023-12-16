import com.common.TextFile
import kotlinx.browser.document
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.dom.addClass
import kotlinx.dom.appendElement
import kotlinx.dom.appendText
import kotlinx.dom.removeClass
import org.w3c.dom.Element
import org.w3c.dom.HTMLInputElement

@OptIn(DelicateCoroutinesApi::class)
class UI(private val api: Api) {
    private var currentTextFile: TextFile
    private val monaco: IStandaloneCodeEditor

    private val saveModal: Element
    private val openModal: Element

    private val openModalFileList: Element
    private val saveModalFileName: HTMLInputElement

    init {
        currentTextFile = TextFile("example.txt", "This is an example file.")

        setMonacoTheme("vs-dark")
        monaco = createMonacoEditor(elById("monaco"))
        monaco.setValue(currentTextFile.content)

        saveModal = elById("save-modal")
        openModal = elById("open-modal")

        openModalFileList = elById("open-modal-file-list")
        saveModalFileName = elById("save-modal-file-name") as HTMLInputElement

        elById("save-modal-open").addEventListener("click") { fileSaveModalOpen() }
        elById("open-modal-open").addEventListener("click") {  GlobalScope.launch { fileOpenModalOpen() } }

        elById("save-modal-close").addEventListener("click") { fileSaveModalClose() }
        elById("open-modal-close").addEventListener("click") { fileOpenModalClose() }

        elById("save-modal-save").addEventListener("click") { GlobalScope.launch { fileSaveModalSave() }  }
    }

    private fun elById(id: String): Element {
        return document.getElementById(id)!!
    }

    private fun fileSaveModalOpen() {
        saveModalFileName.value = currentTextFile.id
        saveModal.addClass("modal-open")
    }

    private fun fileSaveModalClose() {
        saveModal.removeClass("modal-open")
    }

    private suspend fun fileSaveModalSave() {
        currentTextFile.content = monaco.getValue();
        currentTextFile.id = saveModalFileName.value
        api.saveTextFile(currentTextFile)
        fileSaveModalClose()
    }

    private suspend fun fileOpenModalOpen() {
        val textFiles = api.getTextFiles()

        if(textFiles.isEmpty()) {
            openModalFileList.appendText("No files available to load.")
        }

        for (textFile in textFiles) {
            openModalFileList.appendElement("div") {
                appendElement("div") {
                    appendText(textFile.id)
                }

                appendElement("button") {
                    appendText("Open")
                } .addEventListener("click") {
                    currentTextFile = textFile
                    monaco.setValue(currentTextFile.content)
                    fileOpenModalClose()
                }
            } .addClass("file-list-row")
        }

        openModal.addClass("modal-open")
    }

    private fun fileOpenModalClose() {
        openModalFileList.innerHTML = ""
        openModal.removeClass("modal-open")
    }
}


