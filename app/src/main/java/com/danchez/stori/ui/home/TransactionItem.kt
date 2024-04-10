package com.danchez.stori.ui.home

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.danchez.stori.R
import com.danchez.stori.ui.common.ChevronRightIcon
import com.danchez.stori.ui.common.IncomeIcon
import com.danchez.stori.ui.common.WithdrawalIcon
import com.danchez.stori.ui.home.preview.TransactionItemDataProvider
import com.danchez.stori.ui.theme.Spacing
import com.danchez.stori.ui.theme.StoriTheme

@Composable
fun TransactionItem(
    model: TransactionUIModel,
    spacing: Spacing,
    onClick: () -> Unit,
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing.small)
            .clickable(onClick = onClick),
    ) {

        val (transactionIconRef, contentRef, valueRef, chevronIconRef) = createRefs()
        val title: String
        when (model) {
            is IncomeUIModel -> {
                IncomeIcon(
                    modifier = Modifier
                        .constrainAs(transactionIconRef) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(contentRef.start, spacing.extraSmall)
                        },
                )
                title = stringResource(
                    id = R.string.income,
                )
            }

            is WithdrawalUIModel -> {
                WithdrawalIcon(
                    modifier = Modifier
                        .constrainAs(transactionIconRef) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(contentRef.start, spacing.extraSmall)
                        },
                )
                title = "${
                    stringResource(
                        id = R.string.withdrawal_to,
                    )
                } ${model.destinationAccount}"
            }
        }
        Column(
            modifier = Modifier
                .constrainAs(contentRef) {
                    top.linkTo(parent.top)
                    start.linkTo(transactionIconRef.end)
                    end.linkTo(valueRef.start, spacing.extraSmall)
                    width = Dimension.fillToConstraints
                },
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
            )
            if (!model.description.isNullOrBlank()) {
                Spacer(
                    Modifier.height(height = spacing.extraSmall)
                )
                Text(
                    text = model.description!!,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
            }
            Spacer(
                Modifier.height(height = spacing.extraSmall)
            )
            Text(
                text = model.date,
                style = MaterialTheme.typography.bodySmall
            )
        }
        Text(
            modifier = Modifier
                .constrainAs(valueRef) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(contentRef.end)
                    end.linkTo(chevronIconRef.start, spacing.extraSmall)
                },
            text = model.value,
            style = MaterialTheme.typography.bodyLarge,
        )
        ChevronRightIcon(
            modifier = Modifier
                .constrainAs(chevronIconRef) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(valueRef.end)
                    end.linkTo(parent.end)
                },
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun TransactionItemPreview(
    @PreviewParameter(TransactionItemDataProvider::class) model: TransactionUIModel,
) {
    StoriTheme {
        TransactionItem(model = model, spacing = Spacing(), onClick = {})
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun TransactionItemPreviewNight(
    @PreviewParameter(TransactionItemDataProvider::class) model: TransactionUIModel,
) {
    StoriTheme {
        TransactionItem(model = model, spacing = Spacing(), onClick = {})
    }
}
