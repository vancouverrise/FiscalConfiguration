package market.ics.fiscalconfiguration.Driver.DataType;

public enum PaperStatus {
    OK("driver.command.paperstatus.ok"),
    CONNECTION_ERROR("driver.command.paperstatus.errorOfConnectionWithPrinter"),
    PAPER_ALMOST_END("driver.command.paperstatus.receiptPaperIsAlmostEnded"),
    PAPER_IS_FINISHED("driver.command.paperstatus.receiptPaperIsFinished");

    private String statusKey;

    private PaperStatus(String statusKey) {
        this.statusKey = statusKey;
    }

    public String getPaperStatusKey() {
        return this.statusKey;
    }

    public static PaperStatus getStatus(ByteBits b) {
        PaperStatus status = OK;
        if (b.getFirst()) {
            status = CONNECTION_ERROR;
            return status;
        } else if (b.getForth()) {
            status = PAPER_ALMOST_END;
            return status;
        } else if (b.getSeventh()) {
            status = PAPER_IS_FINISHED;
            return status;
        } else {
            return status;
        }
    }
}