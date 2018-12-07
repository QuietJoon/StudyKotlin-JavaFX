import net.sf.sevenzipjbinding.*
import java.io.File
import java.io.IOException
import java.io.RandomAccessFile

import net.sf.sevenzipjbinding.impl.RandomAccessFileInStream
import net.sf.sevenzipjbinding.simple.ISimpleInArchive
import net.sf.sevenzipjbinding.simple.ISimpleInArchiveItem


fun main(args: Array<String>) {
    try {
        SevenZip.initSevenZipFromPlatformJAR()
        println("7-Zip-JBinding library was initialized")
    } catch (e: SevenZipNativeInitializationException) {
        e.printStackTrace()
    }

}

fun testOpener(files: Array<File>) {
    try {
        SevenZip.initSevenZipFromPlatformJAR()
        println("7-Zip-JBinding library was initialized")
    } catch (e: SevenZipNativeInitializationException) {
        e.printStackTrace()
    }

    var randomAccessFile: RandomAccessFile? = null
    var inArchive: IInArchive? = null
    try {
        randomAccessFile = RandomAccessFile(files[0], "r")
        inArchive = SevenZip.openInArchive(null, // autodetect archive type
                RandomAccessFileInStream(randomAccessFile))

        // Getting simple interface of the archive inArchive
        val simpleInArchive = inArchive!!.getSimpleInterface()

        // TODO: How to get CRC which is not of item but of archive itself
        val archiveCRC = inArchive.getArchiveProperty(PropID.CRC)
        println(String.format("Archive CRC: %08X", archiveCRC))
        val archiveCHECKSUM = inArchive.getArchiveProperty(PropID.CHECKSUM)
        println(String.format("Archive CHECKSUM: %08X", archiveCHECKSUM))

        println("   CRC    |   Size    | Compr.Sz. | Filename")
        println("----------+-----------+-----------+---------")

        for (item in simpleInArchive.archiveItems) {
            println(String.format(" %08X | %9s | %9s | %s", //
                    item.crc,
                    item.size,
                    item.packedSize,
                    item.path))
        }
    } catch (e: Exception) {
        System.err.println("Error occurs: $e")
    } finally {
        if (inArchive != null) {
            try {
                inArchive.close()
            } catch (e: SevenZipException) {
                System.err.println("Error closing archive: $e")
            }

        }
        if (randomAccessFile != null) {
            try {
                randomAccessFile.close()
            } catch (e: IOException) {
                System.err.println("Error closing file: $e")
            }

        }
    }
}
