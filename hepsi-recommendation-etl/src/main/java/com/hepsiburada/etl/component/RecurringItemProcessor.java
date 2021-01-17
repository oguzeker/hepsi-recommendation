//package com.hepsiburada.etl.component;
//
//import javax.batch.api.chunk.ItemProcessor;
//
//public class RecurringItemProcessor implements ItemProcessor<RecurringItemRaw, RecurringItem> {
//
//    private static final Logger LOG = Logger.getLogger(RecurringItemProcessor.class.getName());
//
//    @Override
//    public RecurringItem process(final RecurringItemRaw recurringItemRaw) throws Exception {
//        LOG.info("Processing recurring item");
//        final RecurringItem item = new RecurringItem();
//        item.setId(recurringItemRaw.getId());
//        item.setName(recurringItemRaw.getName());
//        item.setAgreementId(recurringItemRaw.getAgreementId());
//        if (recurringItemRaw.getCost().trim().isEmpty()){
//            item.setCost(BigDecimal.ZERO);
//        }
//        else{
//            item.setCost(new BigDecimal(recurringItemRaw.getCost()));
//        }
//        if (recurringItemRaw.getQuantity().trim().isEmpty()){
//            item.setQuantity(0);
//        }else{
//            item.setQuantity(new BigDecimal(recurringItemRaw.getQuantity()).intValue());
//        }
//        LOG.info(item.toString());
//        return item;
//    }
//
//}
